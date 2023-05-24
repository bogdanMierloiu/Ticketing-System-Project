package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.RequestMapper;
import ro.sci.requestservice.model.Request;
import ro.sci.requestservice.model.RequestType;
import ro.sci.requestservice.repository.RequestRepo;
import ro.sci.requestservice.repository.RequestTypeRepo;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepo requestRepo;
    private final RequestTypeRepo requestTypeRepo;
    private final RequestMapper requestMapper;


    public List<RequestResponse> getAllRequests() {
        List<Request> requests = requestRepo.findAllOrderByCreatedAtDesc();
        List<RequestResponse> requestResponses = new ArrayList<>();
        for (var request : requests) {
            RequestResponse requestResponse = requestMapper.map(request);
            requestResponses.add(requestResponse);
        }
        return requestResponses;
    }

    public RequestResponse findById(Long requestId) {
        return requestMapper.map(
                requestRepo.findById(requestId).orElseThrow(
                        () -> new NotFoundException("The request with id " + requestId + " not found")
                )
        );
    }

    public List<RequestResponse> getAllByPolicemanId(Long id){
        List<Request> requests = requestRepo.findAllByPolicemanId(id);
        List<RequestResponse> requestResponses = new ArrayList<>();
        for (var request : requests) {
            RequestResponse requestResponse = requestMapper.map(request);
            requestResponses.add(requestResponse);
        }
        return requestResponses;
    }

    public List<RequestResponse> getAllByPolicemanName(String name){
        String[] nameParts = name.split(" ");

        String lastName = nameParts[0];
        String firstName = "";
        String firstNameSecondary = "";

        if (nameParts.length > 1) {
            firstName = nameParts[1];
        }

        if (nameParts.length > 2) {
            firstNameSecondary = nameParts[2];
        }
        List<Request> requests = requestRepo.findAllByPolicemanName(lastName, firstName, firstNameSecondary);
        List<RequestResponse> requestResponses = new ArrayList<>();
        for (var request : requests) {
            RequestResponse requestResponse = requestMapper.map(request);
            requestResponses.add(requestResponse);
        }
        return requestResponses;
    }


    private RequestType getRequestTypeById(Long requestTypeId) {
        return requestTypeRepo.findById(requestTypeId).orElseThrow(
                () -> new NotFoundException("The request type with id " + requestTypeId + " not found")
        );
    }


}
