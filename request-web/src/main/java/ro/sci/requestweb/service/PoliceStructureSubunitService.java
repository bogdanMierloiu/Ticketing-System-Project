package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.sci.requestweb.dto.PoliceStructureSubunitRequest;
import ro.sci.requestweb.dto.PoliceStructureSubunitResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PoliceStructureSubunitService {

    private final WebClient.Builder webClientBuilder;

    public List<PoliceStructureSubunitResponse> getStructuresByPoliceStation(Long policeStructureId) {
        Flux<PoliceStructureSubunitResponse> responseFlux = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/police-structure-subunit/find/{policeStructureId}", policeStructureId)
                .retrieve()
                .bodyToFlux(PoliceStructureSubunitResponse.class);
        return responseFlux.collectList().block();
    }

    public PoliceStructureSubunitResponse findById(Long subunitId) {
        Mono<PoliceStructureSubunitResponse> mono = webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/police-structure-subunit/{subunitId}", subunitId)
                .retrieve()
                .bodyToMono(PoliceStructureSubunitResponse.class);
        return mono.block();
    }

    @Async
    public void addSubunitStructure(PoliceStructureSubunitRequest structureRequest) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/police-structure-subunit")
                .bodyValue(structureRequest)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
