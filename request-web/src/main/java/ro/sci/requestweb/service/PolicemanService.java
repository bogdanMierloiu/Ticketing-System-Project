package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ro.sci.requestweb.dto.PolicemanResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicemanService {

    private final WebClient.Builder webClientBuilder;

    public List<PolicemanResponse> getAllPolicemen() {
        Flux<PolicemanResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/policeman/all-policemen")
                .retrieve()
                .bodyToFlux(PolicemanResponse.class);
        return responseFlux.collectList().block();
    }

}
