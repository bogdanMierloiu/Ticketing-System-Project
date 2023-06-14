package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.AsyncResponse;
import ro.sci.requestweb.dto.PoliceStructureRequest;
import ro.sci.requestweb.dto.PoliceStructureResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PoliceStructureService {

    private final WebClient.Builder webClientBuilder;


    public List<PoliceStructureResponse> getAllStructures() {
        Flux<PoliceStructureResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/police-structure/all-structures")
                .retrieve()
                .bodyToFlux(PoliceStructureResponse.class);
        return responseFlux.collectList().block();
    }

    public PoliceStructureResponse getById(Long structureId) {
        Mono<PoliceStructureResponse> mono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/police-structure/find/{structureId}", structureId)
                .retrieve()
                .bodyToMono(PoliceStructureResponse.class);
        return mono.block();
    }

    @Async
    public CompletableFuture<AsyncResponse<Void>> addPoliceStructure(PoliceStructureRequest policeStructureRequest) {
        try {
            webClientBuilder.build().post()
                    .uri("lb://request-service/api/v1/police-structure")
                    .bodyValue(policeStructureRequest)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return CompletableFuture.completedFuture(new AsyncResponse<>());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new AsyncResponse<>(null,e));
        }
    }


}
