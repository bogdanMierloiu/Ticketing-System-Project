package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.service.RequestService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v2/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;


    @GetMapping("/all-requests")
    public CompletableFuture<ResponseEntity<List<RequestResponse>>> getAllRequests() {
        CompletableFuture<List<RequestResponse>> future = requestService.getAllRequests();
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/all-requests-in-progress")
    public CompletableFuture<ResponseEntity<List<RequestResponse>>> findNonFinalizedAndRecentRejectedRequests() {
        CompletableFuture<List<RequestResponse>> future = requestService.findNonFinalizedAndRecentRejectedRequests();
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/policeman/{id}")
    public CompletableFuture<ResponseEntity<List<RequestResponse>>> getAllByPolicemanId(@PathVariable("id") Long policemanId) {
        CompletableFuture<List<RequestResponse>> future = requestService.getAllByPolicemanId(policemanId);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/police-structure/{id}")
    public CompletableFuture<ResponseEntity<List<RequestResponse>>> getAllByPoliceStructure(@PathVariable("id") Long structureId) {
        CompletableFuture<List<RequestResponse>> future = requestService.getAllByPoliceStructure(structureId);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/police-subunit/{id}")
    public CompletableFuture<ResponseEntity<List<RequestResponse>>> getAllByPoliceSubunit(@PathVariable("id") Long subunitId) {
        CompletableFuture<List<RequestResponse>> future = requestService.getAllByPoliceSubunit(subunitId);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/specialist/{id}")
    public CompletableFuture<ResponseEntity<List<RequestResponse>>> getAllByItSpecialistId(@PathVariable("id") Long specialistId) {
        CompletableFuture<List<RequestResponse>> future = requestService.findAllByItSpecialistId(specialistId);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/search-by-name")
    public CompletableFuture<ResponseEntity<List<RequestResponse>>> getAllByPolicemanName(@RequestParam String name) {
        CompletableFuture<List<RequestResponse>> future = requestService.getAllByPolicemanName(name);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/find/{requestId}")
    public CompletableFuture<ResponseEntity<RequestResponse>> findById(@PathVariable("requestId") Long requestId) {
        CompletableFuture<RequestResponse> future = requestService.findById(requestId);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/count/all-requests")
    public CompletableFuture<ResponseEntity<Long>> countAllRequests() {
        CompletableFuture<Long> future = requestService.countAllRequests();
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/count/all-requests-in-progress")
    public CompletableFuture<ResponseEntity<Long>> countRequestsInProgress() {
        CompletableFuture<Long> future = requestService.countRequestsInProgress();
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/count/all-requests-finalized")
    public CompletableFuture<ResponseEntity<Long>> countRequestsSuccessFinalized() {
        CompletableFuture<Long> future = requestService.countRequestsSuccessFinalized();
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/count/all-requests-rejected")
    public CompletableFuture<ResponseEntity<Long>> countRequestsRejected() {
        CompletableFuture<Long> future = requestService.countRequestsRejected();
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/get-specialist-id/{requestId}")
    public CompletableFuture<ResponseEntity<Long>> getSpecialistIdByRequest(@PathVariable("requestId") Long requestId) {
        CompletableFuture<Long> future = requestService.getSpecialistIdByRequest(requestId);
        return future.thenApply(ResponseEntity::ok);
    }


}
