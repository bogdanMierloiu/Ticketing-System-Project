package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.RequestMapper;
import ro.sci.requestservice.model.Request;
import ro.sci.requestservice.model.RequestType;
import ro.sci.requestservice.repository.RequestRepo;
import ro.sci.requestservice.repository.RequestTypeRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestService {

    private final RequestRepo requestRepo;
    private final RequestTypeRepo requestTypeRepo;
    private final RequestMapper requestMapper;

    @Async
    @Transactional
    public CompletableFuture<List<RequestResponse>> getAllRequests() {
        List<Request> requests = requestRepo.findAllOrderByCreatedAtDesc();
        List<RequestResponse> requestResponses = new ArrayList<>();
        for (var request : requests) {
            RequestResponse requestResponse = requestMapper.map(request);
            requestResponses.add(requestResponse);
        }
        return CompletableFuture.completedFuture(requestResponses);
    }

    @Async
    public CompletableFuture<List<RequestResponse>> findNonFinalizedAndRecentRejectedRequests() {
        List<Request> requests = requestRepo.findNonFinalizedRequestsOrderByCreatedAtDesc();
        List<RequestResponse> requestResponses = new ArrayList<>();
        for (var request : requests) {
            RequestResponse requestResponse = requestMapper.map(request);
            requestResponses.add(requestResponse);
        }
        return CompletableFuture.completedFuture(requestResponses);
    }

    @Async
    public CompletableFuture<RequestResponse> findById(Long requestId) {
        Request request = requestRepo.findById(requestId).orElseThrow(
                () -> new NotFoundException("The request with id " + requestId + " not found")
        );
        return CompletableFuture.completedFuture(requestMapper.map(request));
    }

    @Async
    public CompletableFuture<List<RequestResponse>> getAllByPolicemanId(Long id) {
        List<Request> requests = requestRepo.findAllByPolicemanId(id);
        List<RequestResponse> requestResponses = new ArrayList<>();
        for (var request : requests) {
            RequestResponse requestResponse = requestMapper.map(request);
            requestResponses.add(requestResponse);
        }
        return CompletableFuture.completedFuture(requestResponses);
    }


    @Async
    public CompletableFuture<List<RequestResponse>> getAllByPoliceStructure(Long id) {
        List<Request> requests = requestRepo.findAllByPoliceStructure(id);
        List<RequestResponse> requestResponses = new ArrayList<>();
        for (var request : requests) {
            RequestResponse requestResponse = requestMapper.map(request);
            requestResponses.add(requestResponse);
        }
        return CompletableFuture.completedFuture(requestResponses);
    }

    @Async
    public CompletableFuture<List<RequestResponse>> getAllByPoliceSubunit(Long id) {
        List<Request> requests = requestRepo.findAllByPoliceSubunit(id);
        List<RequestResponse> requestResponses = new ArrayList<>();
        for (var request : requests) {
            RequestResponse requestResponse = requestMapper.map(request);
            requestResponses.add(requestResponse);
        }
        return CompletableFuture.completedFuture(requestResponses);
    }

    @Async
    public CompletableFuture<List<RequestResponse>> findAllByItSpecialistId(Long id) {
        List<Request> requests = requestRepo.findAllByItSpecialistId(id);
        List<RequestResponse> requestResponses = new ArrayList<>();
        for (var request : requests) {
            RequestResponse requestResponse = requestMapper.map(request);
            requestResponses.add(requestResponse);
        }
        return CompletableFuture.completedFuture(requestResponses);
    }




    @Async
    public CompletableFuture<List<RequestResponse>> getAllByPolicemanName(String name) {
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
        return CompletableFuture.completedFuture(requestResponses);
    }

    @Async
    public CompletableFuture<Long> countAllRequests() {
        return CompletableFuture.completedFuture(requestRepo.countAllRequests());
    }
    @Async
    public CompletableFuture<Long> countRequestsInProgress() {
        return CompletableFuture.completedFuture(requestRepo.countRequestsInProgress());
    }
    @Async
    public CompletableFuture<Long> countRequestsSuccessFinalized() {
        return CompletableFuture.completedFuture(requestRepo.countRequestsSuccessFinalized());
    }
    @Async
    public CompletableFuture<Long> countRequestsRejected() {
        return CompletableFuture.completedFuture(requestRepo.countRequestsRejected());
    }

    // UTILS

    private RequestType getRequestTypeById(Long requestTypeId) {
        return requestTypeRepo.findById(requestTypeId).orElseThrow(
                () -> new NotFoundException("The request type with id " + requestTypeId + " not found")
        );
    }
}
