package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.AccountRequest;
import ro.sci.requestweb.dto.RequestResponse;
import ro.sci.requestweb.exception.AlreadyHaveThisRequestException;
import ro.sci.requestweb.exception.UnsupportedOperationException;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final WebClient.Builder webClientBuilder;
    private final Object lock = new Object();

    public Flux<RequestResponse> getAllRequests() {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/all-requests")
                .retrieve()
                .bodyToFlux(RequestResponse.class);
    }

    public Flux<RequestResponse> getAllRequestsInProgress() {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/all-requests-in-progress")
                .retrieve()
                .bodyToFlux(RequestResponse.class);
    }


    public Flux<RequestResponse> getAllRequestsByPolicemanName(String name) {
        return webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("lb")
                        .host("request-query-service")
                        .path("/api/v2/request/search-by-name")
                        .queryParam("name", name)
                        .build())
                .retrieve()
                .bodyToFlux(RequestResponse.class);
    }

    public Flux<RequestResponse> getAllRequestsByPolicemanId(Long policemanId) {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/policeman/{policemanId}", policemanId)
                .retrieve()
                .bodyToFlux(RequestResponse.class);
    }

    public Flux<RequestResponse> getAllRequestsByPoliceStructure(Long policeStructureId) {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/police-structure/{policeStructureId}", policeStructureId)
                .retrieve()
                .bodyToFlux(RequestResponse.class);
    }

    public Flux<RequestResponse> getAllRequestsByPoliceSubunit(Long subunitId) {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/police-subunit/{subunitId}", subunitId)
                .retrieve()
                .bodyToFlux(RequestResponse.class);
    }

    public Mono<RequestResponse> findById(Long requestId) {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/find/{requestId}", requestId)
                .retrieve()
                .bodyToMono(RequestResponse.class);
    }

    @Async
    public void addRequest(AccountRequest request) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/request")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus.CONFLICT::equals, clientResponse -> {
                    throw new AlreadyHaveThisRequestException("Pentru acest politist, exista deja o solicitare de acest tip in lucru!");
                })
                .toBodilessEntity()
                .then();
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
                    .retrieve()
                    .onStatus(HttpStatus.CONFLICT::equals, clientResponse -> clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new UnsupportedOperationException(errorBody))))
                    .toBodilessEntity()
                    .block();
        }
    }

}
