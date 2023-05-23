package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sci.requestweb.dto.RankRequest;
import ro.sci.requestweb.dto.RankResponse;

@Service
@RequiredArgsConstructor
public class RankService {

    private final WebClient.Builder webClientBuilder;

    public RankResponse[] getAllRanks() {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/rank/all-ranks")
                .retrieve()
                .bodyToMono(RankResponse[].class)
                .block();
    }
    public void addRank(RankRequest request) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/rank")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
