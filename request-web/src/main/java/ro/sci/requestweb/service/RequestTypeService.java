package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ro.sci.requestweb.dto.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RequestTypeService {

    private final WebClient.Builder webClientBuilder;
    private final String key = System.getenv("api_key");

    public List<RequestTypeResponse> getAllRequestTypes() {
        Flux<RequestTypeResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request-type/all-request-types")
                .retrieve()
                .bodyToFlux(RequestTypeResponse.class);
        return responseFlux.collectList().block();
    }

    @Async
    public CompletableFuture<AsyncResponse<Void>> addRequestType(RequestTypeReq request) {
        try {
            webClientBuilder.build().post()
                    .uri("lb://request-service/api/v1/request-type")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return CompletableFuture.completedFuture(new AsyncResponse<>());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AsyncResponse<>(null, e));
        }
    }


}
