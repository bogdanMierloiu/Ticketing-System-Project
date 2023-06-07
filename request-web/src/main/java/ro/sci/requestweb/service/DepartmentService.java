package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.DepartmentRequest;
import ro.sci.requestweb.dto.DepartmentResponse;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final WebClient.Builder webClientBuilder;

    public Mono<DepartmentResponse[]> getBySubunit(Long subunitId) {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/department/{subunitId}", subunitId)
                .retrieve()
                .bodyToMono(DepartmentResponse[].class);
    }
    @Async
    public void addDepartment(DepartmentRequest departmentRequest) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/department")
                .body(BodyInserters.fromValue(departmentRequest))
                .retrieve()
                .bodyToMono(Void.class);
    }

}
