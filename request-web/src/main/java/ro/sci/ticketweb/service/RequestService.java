package ro.sci.ticketweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sci.ticketweb.dto.RequestResponse;

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

//    public TicketResponse[] getTicketsByItSpecialistId(Long itSpecialistId) {
//        return webClientBuilder.build().get()
//                .uri("lb://ticket-service/api/it-specialist/{workerId}", itSpecialistId)
//                .retrieve()
//                .bodyToMono(TicketResponse[].class)
//                .block();
//    }
//
//    public TicketResponse getTicketById(Long ticketId) {
//        return webClientBuilder.build().get()
//                .uri("lb://ticket-service/api/{ticketId}", ticketId)
//                .retrieve()
//                .bodyToMono(TicketResponse.class)
//                .block();
//    }
//
//    public void addTicket(TicketRequest request) {
//        webClientBuilder.build().post()
//                .uri("lb://ticket-service/api")
//                .bodyValue(request)
//                .retrieve()
//                .toBodilessEntity()
//                .block();
//    }
//
//    public void assignTicket(AssignTicketRequest request) {
//        webClientBuilder.build().post()
//                .uri("lb://ticket-service/api/assign")
//                .bodyValue(request)
//                .retrieve()
//                .toBodilessEntity()
//                .block();
//    }

}
