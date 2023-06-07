package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ro.sci.requestweb.dto.ItSpecialistRequest;
import ro.sci.requestweb.dto.ItSpecialistResponse;

@Service
@RequiredArgsConstructor
public class ItSpecialistService {

    private final WebClient.Builder webClientBuilder;

    public Flux<ItSpecialistResponse> getAllSpecialists() {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/it-specialist/all-specialists")
                .retrieve()
                .bodyToFlux(ItSpecialistResponse.class);
    }

    @Async
    public void addSpecialist(ItSpecialistRequest request) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/it-specialist")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(Void.class);
    }
}
