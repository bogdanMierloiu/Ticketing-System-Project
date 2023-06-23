package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.AsyncResponse;
import ro.sci.requestweb.dto.RequestTypeReq;
import ro.sci.requestweb.dto.RequestTypeResponse;

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
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(RequestTypeResponse.class);
        return responseFlux.collectList().block();
    }

    public RequestTypeResponse getById(Long requestTypeId) {
        Mono<RequestTypeResponse> responseMono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request-type/{requestTypeId}", requestTypeId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(RequestTypeResponse.class);
        return responseMono.block();
    }

    @Async
    public CompletableFuture<AsyncResponse<Void>> addRequestType(RequestTypeReq request) {
        try {
            webClientBuilder.build().post()
                    .uri("lb://request-service/api/v1/request-type")
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
