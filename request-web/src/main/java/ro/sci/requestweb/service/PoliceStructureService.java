package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sci.requestweb.dto.PoliceStructureRequest;
import ro.sci.requestweb.dto.PoliceStructureResponse;

@Service
@RequiredArgsConstructor
public class PoliceStructureService {

    private final WebClient.Builder webClientBuilder;

    public PoliceStructureResponse[] getAllStructures() {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/police-structure/all-structures")
                .retrieve()
                .bodyToMono(PoliceStructureResponse[].class)
                .block();
    }

    public PoliceStructureResponse getById(Long structureId) {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/police-structure/find/{structureId}", structureId)
                .retrieve()
                .bodyToMono(PoliceStructureResponse.class)
                .block();
    }

    public void addPoliceStructure(PoliceStructureRequest policeStructureRequest) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/police-structure")
                .bodyValue(policeStructureRequest)
                .retrieve()
                .toBodilessEntity()
                .block();
    }


}
