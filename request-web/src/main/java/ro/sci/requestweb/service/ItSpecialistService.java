package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sci.requestweb.dto.ItSpecialistRequest;
import ro.sci.requestweb.dto.ItSpecialistResponse;

@Service
@RequiredArgsConstructor
public class ItSpecialistService {

    private final WebClient.Builder webClientBuilder;

    public ItSpecialistResponse[] getAllSpecialists() {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/it-specialist/all-specialists")
                .retrieve()
                .bodyToMono(ItSpecialistResponse[].class)
                .block();
    }

    public void addSpecialist(ItSpecialistRequest request) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/it-specialist")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }


}
