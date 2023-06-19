package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.AsyncResponse;
import ro.sci.requestweb.dto.PoliceStructureSubunitRequest;
import ro.sci.requestweb.dto.PoliceStructureSubunitResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PoliceStructureSubunitService {

    private final WebClient.Builder webClientBuilder;
    private final String key = System.getenv("api_key");

    public List<PoliceStructureSubunitResponse> getStructuresByPoliceStation(Long policeStructureId) {
        Flux<PoliceStructureSubunitResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/police-structure-subunit/find/{policeStructureId}", policeStructureId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToFlux(PoliceStructureSubunitResponse.class);
        return responseFlux.collectList().block();
    }

    public PoliceStructureSubunitResponse findById(Long subunitId) {
        Mono<PoliceStructureSubunitResponse> mono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/police-structure-subunit/{subunitId}", subunitId)
                .header("X-Api-Key", key)
                .retrieve()
                .bodyToMono(PoliceStructureSubunitResponse.class);
        return mono.block();
    }

    @Async
    public CompletableFuture<AsyncResponse<Void>> addSubunitStructure(PoliceStructureSubunitRequest structureRequest) {
        try {
            webClientBuilder.build().post()
                    .uri("lb://request-service/api/v1/police-structure-subunit")
                    .bodyValue(structureRequest)
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
