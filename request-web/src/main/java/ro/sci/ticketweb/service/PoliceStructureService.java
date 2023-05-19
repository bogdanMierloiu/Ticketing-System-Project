package ro.sci.ticketweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ro.sci.ticketweb.dto.PoliceStructureRequest;
import ro.sci.ticketweb.dto.PoliceStructureResponse;

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

//    public RequestResponse findById(Long requestId) {
//        return webClientBuilder.build().get()
//                .uri("lb://request-query-service/api/v2/request/find/{requestId}", requestId)
//                .retrieve()
//                .bodyToMono(RequestResponse.class)
//                .block();
//    }

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
    public void addPoliceStructure(PoliceStructureRequest policeStructureRequest) {
        webClientBuilder.build().post()
                .uri("lb://request-service/api/v1/police-structure")
                .bodyValue(policeStructureRequest)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

//    public void assignTicket(AssignTicketRequest request) {
//        webClientBuilder.build().post()
//                .uri("lb://ticket-service/api/assign")
//                .bodyValue(request)
//                .retrieve()
//                .toBodilessEntity()
//                .block();
//    }

}
