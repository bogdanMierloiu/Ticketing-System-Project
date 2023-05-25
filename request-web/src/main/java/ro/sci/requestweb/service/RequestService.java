package ro.sci.requestweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sci.requestweb.dto.AccountRequest;
import ro.sci.requestweb.dto.RequestResponse;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final WebClient.Builder webClientBuilder;

    public RequestResponse[] getAllRequests() {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/all-requests")
                .retrieve()
                .bodyToMono(RequestResponse[].class)
                .block();
    }

    public RequestResponse[] getAllRequestsByPolicemanName(String name) {
        return webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("lb")
                        .host("request-query-service")
                        .path("/api/v2/request/search-by-name")
                        .queryParam("name", name)
                        .build())
                .retrieve()
                .bodyToMono(RequestResponse[].class)
                .block();
    }

    public RequestResponse[] getAllRequestsByPolicemanId(Long policemanId) {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/policeman/{policemanId}", policemanId)
                .retrieve()
                .bodyToMono(RequestResponse[].class)
                .block();
    }

    public RequestResponse findById(Long requestId) {
        return webClientBuilder.build().get()
                .uri("lb://request-query-service/api/v2/request/find/{requestId}", requestId)
                .retrieve()
                .bodyToMono(RequestResponse.class)
                .block();
    }

    public void addRequest(AccountRequest request) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/request")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    // POLICE STRUCTURE

    public void structureChiefApprove(Long requestId) {
        webClientBuilder.build().patch()
                .uri(uriBuilder -> uriBuilder
                        .scheme("lb")
                        .host("request-service")
                        .path("/api/v1/request/structure-chief-approve/{requestId}")
                        .build(requestId))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void structureChiefReject(Long requestId, String observation) {
        webClientBuilder.build().put()
                .uri(uriBuilder -> uriBuilder
                        .scheme("lb")
                        .host("request-service")
                        .path("/api/v1/request/structure-chief-reject/{requestId}")
                        .queryParam("observation", observation)
                        .build(requestId))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    // SECURITY STRUCTURE

    public void securityApprove(Long requestId) {
        webClientBuilder.build().patch()
                .uri(uriBuilder -> uriBuilder
                        .scheme("lb")
                        .host("request-service")
                        .path("/api/v1/request/security-approve/{requestId}")
                        .build(requestId))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void securityReject(Long requestId, String observation) {
        webClientBuilder.build().put()
                .uri(uriBuilder -> uriBuilder
                        .scheme("lb")
                        .host("request-service")
                        .path("/api/v1/request/security-reject/{requestId}")
                        .queryParam("observation", observation)
                        .build(requestId))
                .retrieve()
                .toBodilessEntity()
                .block();
    }


}
