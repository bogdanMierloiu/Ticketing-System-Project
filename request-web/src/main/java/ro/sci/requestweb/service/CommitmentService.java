package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.CommitmentResponse;

@Service
@RequiredArgsConstructor
public class CommitmentService {

    private final WebClient.Builder webClientBuilder;
    private final String key = System.getenv("api_key");

//    @Transactional
    public CommitmentResponse findByRequest(Long requestId) {
        Mono<CommitmentResponse> mono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/commitment/find-by-request/{requestId}", requestId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(CommitmentResponse.class);
        return mono.block();
    }


}
