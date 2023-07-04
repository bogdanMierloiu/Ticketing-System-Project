package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.AsyncResponse;
import ro.sci.requestweb.dto.ItSpecialistRequest;
import ro.sci.requestweb.dto.ItSpecialistResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ItSpecialistService {

    private final WebClient.Builder webClientBuilder;

    private final String key = System.getenv("api_key");

    public List<ItSpecialistResponse> getAllSpecialists() {
        Flux<ItSpecialistResponse> responseFLux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/it-specialist/all-specialists")
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(ItSpecialistResponse.class);
        return responseFLux.collectList().block();
    }

    public ItSpecialistResponse findByName(String lastName) {
        Mono<ItSpecialistResponse> responseMono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/it-specialist/find/{lastName}", lastName)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(ItSpecialistResponse.class);
        return responseMono.block();
    }

    public ItSpecialistResponse findById(Long specialistId) {
        Mono<ItSpecialistResponse> responseMono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/it-specialist/find-id/{specialistId}", specialistId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(ItSpecialistResponse.class);
        return responseMono.block();
    }

    @Async
    public CompletableFuture<AsyncResponse<Void>> addSpecialist(ItSpecialistRequest request) {
        try {
            webClientBuilder.build().post()
                    .uri("lb://request-service/api/v1/it-specialist")
                    .body(BodyInserters.fromValue(request))
                    .header("X-Api-Key", key)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return CompletableFuture.completedFuture(new AsyncResponse<>());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AsyncResponse<>(null, e));
        }
    }

    @Async
    public CompletableFuture<AsyncResponse<Void>> update(ItSpecialistRequest request) {
        try {
            webClientBuilder.build().put()
                    .uri("lb://request-service/api/v1/it-specialist")
                    .body(BodyInserters.fromValue(request))
                    .header("X-Api-Key", key)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return CompletableFuture.completedFuture(new AsyncResponse<>());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AsyncResponse<>(null, e));
        }
    }

    @Async
    public CompletableFuture<AsyncResponse<Void>> delete(Long specialistId) {
        try {
            webClientBuilder.build().delete()
                    .uri("lb://request-service/api/v1/it-specialist/{id}", specialistId)
                    .header("X-Api-Key", key)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return CompletableFuture.completedFuture(new AsyncResponse<>());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AsyncResponse<>(null, e));
        }
    }


    public Long countAllRequests(Long specialistId) {
        Mono<Long> response = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/it-specialist/count/all-requests/{specialistId}", specialistId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(Long.class);
        return response.block();
    }

    public Long countAllRequestsInProgress(Long specialistId) {
        Mono<Long> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/it-specialist/count/all-requests-in-progress/{specialistId}", specialistId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(Long.class);
        return responseFlux.block();
    }

    public Long countAllRequestsFinalized(Long specialistId) {
        Mono<Long> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/it-specialist/count/all-requests-finalized/{specialistId}", specialistId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(Long.class);
        return responseFlux.block();
    }


}
