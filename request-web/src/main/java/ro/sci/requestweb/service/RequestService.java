package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.AccountRequestToSend;
import ro.sci.requestweb.dto.AsyncResponse;
import ro.sci.requestweb.dto.RequestResponse;
import ro.sci.requestweb.exception.AlreadyHaveThisRequestException;
import ro.sci.requestweb.exception.UnsupportedOperationException;

import java.util.Calendar;
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

    public List<RequestResponse> getAllRequestsByItSpecialist(Long specialistId) {
        Flux<RequestResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/specialist/{specialistId}", specialistId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(RequestResponse.class);
        return collectToList(responseFlux);
    }

    public RequestResponse findById(Long requestId) {
        Mono<RequestResponse> mono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/find/{requestId}", requestId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(RequestResponse.class);
        return mono.block();
    }


    public Boolean checkExistingRequest(Long requestTypeId, String policemanCNP) {
        Mono<Boolean> mono = webClientBuilder.build()
                .post()
                .uri("lb://request-service/api/v1/request/check-existing-request?requestTypeId={requestTypeId}&policemanCNP={policemanCNP}", requestTypeId, policemanCNP)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(Boolean.class);
        return mono.block();
    }


    @Async
    public CompletableFuture<AsyncResponse<Void>> addRequest(AccountRequestToSend request) throws AlreadyHaveThisRequestException {
        try {
            webClientBuilder.build().post()
                    .uri("lb://request-service/api/v1/request")
                    .bodyValue(request)
                    .header("X-Api-Key", key)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return CompletableFuture.completedFuture(new AsyncResponse<>(null, null));
        } catch (Exception e) {
            throw new RuntimeException("A apărut o eroare la procesarea cererii!");
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


    // COUNTERS

    public Long countAllRequests() {
        Mono<Long> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/count/all-requests")
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(Long.class);
        return responseFlux.block();
    }

    public Long countRequestsInProgress() {
        Mono<Long> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/count/all-requests-in-progress")
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(Long.class);
        return responseFlux.block();
    }

    public Long countRequestsSuccessFinalized() {
        Mono<Long> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/count/all-requests-finalized")
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(Long.class);
        return responseFlux.block();
    }

    public Long countRequestsRejected() {
        Mono<Long> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/count/all-requests-rejected")
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(Long.class);
        return responseFlux.block();
    }

    public Long getSpecialistIdByRequest(Long requestId) {
        Mono<Long> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/get-specialist-id/{requestId}", requestId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(Long.class);
        return responseFlux.block();
    }


    // UTILS
    private <T> List<T> collectToList(Flux<T> flux) {
        return flux.collectList().block();
    }

    public boolean isValidCNP(String cnp) {
        // Verificăm dimensiunea CNP-ului
        if (cnp.length() != 13) {
            return false;
        }

        // Verificăm prima cifră
        char firstDigit = cnp.charAt(0);
        boolean isMale = firstDigit == '1' || firstDigit == '5';
        boolean isFemale = firstDigit == '2' || firstDigit == '6';
        if (!isMale && !isFemale) {
            return false;
        }

        // Verificăm celelalte cifre
        try {
            int year = Integer.parseInt(cnp.substring(1, 3));
            year += firstDigit == '5' || firstDigit == '6' ? 2000 : 1900;
            int month = Integer.parseInt(cnp.substring(3, 5));
            int day = Integer.parseInt(cnp.substring(5, 7));
            int countyCode = Integer.parseInt(cnp.substring(7, 9));
            int orderNumber = Integer.parseInt(cnp.substring(9, 12));

            if (year < 0 || year > 9999 || month < 1 || month > 12 ||
                    day < 1 || day > 31 || countyCode < 1 || countyCode > 52 ||
                    orderNumber < 1 || orderNumber > 999) {
                return false;
            }

            // Verificăm anul de naștere în funcție de prima cifră
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            boolean isBornBefore2000 = firstDigit == '1' || firstDigit == '2';
            boolean isBornAfter2000 = firstDigit == '5' || firstDigit == '6';
            if ((isBornBefore2000 && (year < 1900 || year > currentYear - 18)) || (isBornAfter2000 && (year < 2000 || year > currentYear - 18))) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // CNP-ul a trecut toate verificările
        return true;
    }

}
