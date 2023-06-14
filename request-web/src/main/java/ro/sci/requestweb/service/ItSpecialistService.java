package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ro.sci.requestweb.dto.AsyncResponse;
import ro.sci.requestweb.dto.ItSpecialistRequest;
import ro.sci.requestweb.dto.ItSpecialistResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ItSpecialistService {

    private final WebClient.Builder webClientBuilder;

    public List<ItSpecialistResponse> getAllSpecialists() {
        Flux<ItSpecialistResponse> responseFLux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/it-specialist/all-specialists")
                .retrieve()
                .bodyToFlux(ItSpecialistResponse.class);
        return responseFLux.collectList().block();
    }

    @Async
    public CompletableFuture<AsyncResponse<Void>> addSpecialist(ItSpecialistRequest request) {
        try {
            webClientBuilder.build().post()
                    .uri("lb://request-service/api/v1/it-specialist")
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return CompletableFuture.completedFuture(new AsyncResponse<>());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AsyncResponse<>(null, e));
        }
    }


}
