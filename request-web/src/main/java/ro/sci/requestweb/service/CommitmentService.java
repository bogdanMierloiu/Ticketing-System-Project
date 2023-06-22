package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.AsyncResponse;
import ro.sci.requestweb.dto.CommitmentRequestToSend;
import ro.sci.requestweb.dto.CommitmentResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CommitmentService {

    private final WebClient.Builder webClientBuilder;
    private final String key = System.getenv("api_key");


    public CommitmentResponse findByRequest(Long requestId) {
        Mono<CommitmentResponse> mono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/commitment/find-by-request/{requestId}", requestId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(CommitmentResponse.class);
        return mono.block();
    }
    @Async
    public CompletableFuture<AsyncResponse<Void>> addCommitment(CommitmentRequestToSend commitmentRequest) {
        try {
            webClientBuilder.build().post()
                    .uri("lb://request-service/api/v1/commitment")
                    .body(BodyInserters.fromValue(commitmentRequest))
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
    public CompletableFuture<AsyncResponse<Void>> updateCommitment(CommitmentRequestToSend commitmentRequest) {
        try {
            webClientBuilder.build().put()
                    .uri("lb://request-service/api/v1/commitment")
                    .body(BodyInserters.fromValue(commitmentRequest))
                    .header("X-Api-Key", key)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return CompletableFuture.completedFuture(new AsyncResponse<>());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AsyncResponse<>(null, e));
        }
    }
    public List<CommitmentResponse> getAllCommitmentsFromAdmin() {
        Flux<CommitmentResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/commitment/all-commitments")
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(CommitmentResponse.class);
        return collectToList(responseFlux);
    }
    public CommitmentResponse getCommitmentById(Long commitmentId) {
        Mono<CommitmentResponse> mono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/commitment/{commitmentId}", commitmentId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(CommitmentResponse.class);
        return mono.block();
    }

    // UTILS
    private <T> List<T> collectToList(Flux<T> flux) {
        return flux.collectList().block();
    }



}
