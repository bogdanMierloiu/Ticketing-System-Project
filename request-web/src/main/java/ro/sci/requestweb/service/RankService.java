package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ro.sci.requestweb.dto.AsyncResponse;
import ro.sci.requestweb.dto.RankRequest;
import ro.sci.requestweb.dto.RankResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RankService {

    private final WebClient.Builder webClientBuilder;
    private final String key = System.getenv("api_key");
    public List<RankResponse> getAllRanks() {
        Flux<RankResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/rank/all-ranks")
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(RankResponse.class);
        return responseFlux.collectList().block();
    }
    @Async
    public CompletableFuture<AsyncResponse<Void>> addRank(RankRequest request) {
        try {

            webClientBuilder.build().post()
                    .uri("lb://request-service/api/v1/rank")
                    .bodyValue(request)
                    .header("X-Api-Key", key)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return CompletableFuture.completedFuture(new AsyncResponse<>());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AsyncResponse<>(null, e));
        }
    }

}
