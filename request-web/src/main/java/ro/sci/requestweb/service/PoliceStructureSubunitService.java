package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sci.requestweb.dto.PoliceStructureSubunitResponse;

@Service
@RequiredArgsConstructor
public class PoliceStructureSubunitService {

    private final WebClient.Builder webClientBuilder;

    public PoliceStructureSubunitResponse[] getStructuresByPoliceStation(Long policeStructureId) {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/police-structure-subunit/find/{policeStructureId}", policeStructureId)
                .retrieve()
                .bodyToMono(PoliceStructureSubunitResponse[].class)
                .block();
    }

    public PoliceStructureSubunitResponse findById(Long subunitId) {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/police-structure-subunit/{subunitId}", subunitId)
                .retrieve()
                .bodyToMono(PoliceStructureSubunitResponse.class)
                .block();
    }


}
