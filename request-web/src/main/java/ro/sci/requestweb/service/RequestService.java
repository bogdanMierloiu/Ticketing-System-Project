package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.AccountRequest;
import ro.sci.requestweb.dto.AsyncResponse;
import ro.sci.requestweb.dto.RequestResponse;
import ro.sci.requestweb.exception.AlreadyHaveThisRequestException;
import ro.sci.requestweb.exception.UnsupportedOperationException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final WebClient.Builder webClientBuilder;
    private final Object lock = new Object();
    private final String key = System.getenv("api_key");


    public List<RequestResponse> getAllRequests() {
        Flux<RequestResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/all-requests")
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(RequestResponse.class);
        return collectToList(responseFlux);
    }

    public List<RequestResponse> getAllRequestsInProgress() {
        Flux<RequestResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/all-requests-in-progress")
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(RequestResponse.class);
        return collectToList(responseFlux);
    }


    public List<RequestResponse> getAllRequestsByPolicemanName(String name) {
        Flux<RequestResponse> responseFlux = webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("lb")
                        .host("request-query-service")
                        .path("/api/v2/request/search-by-name")
                        .queryParam("name", name)
                        .build())
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(RequestResponse.class);
        return collectToList(responseFlux);
    }

    public List<RequestResponse> getAllRequestsByPolicemanId(Long policemanId) {
        Flux<RequestResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/policeman/{policemanId}", policemanId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(RequestResponse.class);
        return collectToList(responseFlux);
    }

    public List<RequestResponse> getAllRequestsByPoliceStructure(Long policeStructureId) {
        Flux<RequestResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/police-structure/{policeStructureId}", policeStructureId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(RequestResponse.class);
        return collectToList(responseFlux);
    }

    public List<RequestResponse> getAllRequestsByPoliceSubunit(Long subunitId) {
        Flux<RequestResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/police-subunit/{subunitId}", subunitId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(RequestResponse.class);
        return collectToList(responseFlux);
    }

    public RequestResponse findById(Long requestId) {
        Mono<RequestResponse> mono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/find/{requestId}", requestId)
                .header("X-Api-Key", key)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(RequestResponse.class);
        return mono.block();
    }

    @Async
    public CompletableFuture<AsyncResponse<Void>> addRequest(AccountRequest request) throws AlreadyHaveThisRequestException {
        try {
            webClientBuilder.build().post()
                    .uri("lb://request-service/api/v1/request")
                    .bodyValue(request)
                    .header("X-Api-Key", key)
                    .retrieve()
                    .onStatus(HttpStatus.CONFLICT::equals, clientResponse -> {
                        throw new AlreadyHaveThisRequestException("Pentru acest politist, exista deja o solicitare de acest tip in lucru!");
                    })
                    .toBodilessEntity()
                    .block();
            return CompletableFuture.completedFuture(new AsyncResponse<>(null, null));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AsyncResponse<>(null, e));
        }
    }


    // POLICE STRUCTURE

    public synchronized void structureChiefApprove(Long requestId) {
        synchronized (lock) {
            webClientBuilder.build().patch()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("lb")
                            .host("request-service")
                            .path("/api/v1/request/structure-chief-approve/{requestId}")
                            .build(requestId))
                    .header("X-Api-Key", key)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }
    }

    public synchronized void structureChiefReject(Long requestId, String observation) {
        synchronized (lock) {
            webClientBuilder.build().put()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("lb")
                            .host("request-service")
                            .path("/api/v1/request/structure-chief-reject/{requestId}")
                            .queryParam("observation", observation)
                            .build(requestId))
                    .header("X-Api-Key", key)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }
    }

    // SECURITY STRUCTURE


    public synchronized void securityApprove(Long requestId) {
        synchronized (lock) {
            webClientBuilder.build().patch()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("lb")
                            .host("request-service")
                            .path("/api/v1/request/security-approve/{requestId}")
                            .build(requestId))
                    .header("X-Api-Key", key)
                    .retrieve()
                    .onStatus(HttpStatus.CONFLICT::equals, clientResponse -> {
                        throw new UnsupportedOperationException("Solicitarea nu este aprobata de seful structurii de politie emitente!");
                    })
                    .toBodilessEntity()
                    .block();
        }
    }


    public synchronized void securityReject(Long requestId, String observation) {
        synchronized (lock) {
            webClientBuilder.build().put()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("lb")
                            .host("request-service")
                            .path("/api/v1/request/security-reject/{requestId}")
                            .queryParam("observation", observation)
                            .build(requestId))
                    .header("X-Api-Key", key)
                    .retrieve()
                    .onStatus(HttpStatus.CONFLICT::equals, clientResponse -> {
                        throw new UnsupportedOperationException("Solicitarea nu este aprobata de seful structurii de politie emitente!");
                    })
                    .toBodilessEntity()
                    .block();
        }
    }

    // IT STRUCTURE

    public synchronized void itApprove(Long requestId, Long itSpecialistId) {
        synchronized (lock) {
            webClientBuilder.build().put()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("lb")
                            .host("request-service")
                            .path("/api/v1/request/it-approve/{requestId}/{itSpecialistId}")
                            .build(requestId, itSpecialistId))
                    .header("X-Api-Key", key)
                    .retrieve()
                    .onStatus(HttpStatus.CONFLICT::equals, clientResponse -> {
                        throw new UnsupportedOperationException("Solicitarea nu este aprobata de structura de securitate!");
                    })
                    .toBodilessEntity()
                    .block();
        }
    }

    public synchronized void itReject(Long requestId, String observation) {
        synchronized (lock) {
            webClientBuilder.build().put()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("lb")
                            .host("request-service")
                            .path("/api/v1/request/it-reject/{requestId}")
                            .queryParam("observation", observation)
                            .build(requestId))
                    .header("X-Api-Key", key)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }
    }

    public synchronized void finalize(Long requestId) {
        synchronized (lock) {
            webClientBuilder.build().patch()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("lb")
                            .host("request-service")
                            .path("/api/v1/request/finalize/{requestId}")
                            .build(requestId))
                    .header("X-Api-Key", key)
                    .retrieve()
                    .onStatus(HttpStatus.CONFLICT::equals, clientResponse -> clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new UnsupportedOperationException(errorBody))))
                    .toBodilessEntity()
                    .block();
        }
    }

    // UTILS
    private <T> List<T> collectToList(Flux<T> flux) {
        return flux.collectList().block();
    }

}
