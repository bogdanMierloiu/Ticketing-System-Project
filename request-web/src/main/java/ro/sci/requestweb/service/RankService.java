package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ro.sci.requestweb.dto.RankRequest;
import ro.sci.requestweb.dto.RankResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {

    private final WebClient.Builder webClientBuilder;

    public List<RankResponse> getAllRanks() {
        Flux<RankResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/rank/all-ranks")
                .retrieve()
                .bodyToFlux(RankResponse.class);
        return responseFlux.collectList().block();
    }

    @Async
    public void addRank(RankRequest request) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/rank")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
