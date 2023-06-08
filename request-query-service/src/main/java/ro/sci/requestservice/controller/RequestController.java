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

}
