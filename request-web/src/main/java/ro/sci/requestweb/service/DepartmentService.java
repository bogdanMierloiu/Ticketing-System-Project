package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ro.sci.requestweb.dto.DepartmentRequest;
import ro.sci.requestweb.dto.DepartmentResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final WebClient.Builder webClientBuilder;

    public List<DepartmentResponse> getBySubunit(Long subunitId) {
        Flux<DepartmentResponse> departmentResponseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/department/{subunitId}", subunitId)
                .retrieve()
                .bodyToFlux(DepartmentResponse.class);
        return departmentResponseFlux.collectList().block();
    }

    @Async
    public void addDepartment(DepartmentRequest departmentRequest) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/department")
                .body(BodyInserters.fromValue(departmentRequest))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
