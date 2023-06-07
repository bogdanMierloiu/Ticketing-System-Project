package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sci.requestweb.dto.RequestTypeReq;
import ro.sci.requestweb.dto.RequestTypeResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestTypeService {

    private final WebClient.Builder webClientBuilder;

    public Mono<RequestTypeResponse[]> getAllRequestTypes() {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request-type/all-request-types")
                .retrieve()
                .bodyToMono(RequestTypeResponse[].class);
    }
    @Async
    public void addRequestType(RequestTypeReq request) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/request-type")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
