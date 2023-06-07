package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.PolicemanResponse;

@Service
@RequiredArgsConstructor
public class PolicemanService {

    private final WebClient.Builder webClientBuilder;

    public Mono<PolicemanResponse[]> getAllPolicemen() {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/policeman/all-policemen")
                .retrieve()
                .bodyToMono(PolicemanResponse[].class);
    }

}
