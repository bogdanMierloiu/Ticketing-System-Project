package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.service.RequestService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v2/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/all-requests")
    public ResponseEntity<List<RequestResponse>> getAllRequests() throws ExecutionException, InterruptedException {
        CompletableFuture<List<RequestResponse>> future = requestService.getAllRequests();
        List<RequestResponse> requests = future.get();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/all-requests-in-progress")
    public ResponseEntity<List<RequestResponse>> findNonFinalizedAndRecentRejectedRequests() throws ExecutionException, InterruptedException {
        CompletableFuture<List<RequestResponse>> future = requestService.findNonFinalizedAndRecentRejectedRequests();
        List<RequestResponse> requests = future.get();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/policeman/{id}")
    public ResponseEntity<List<RequestResponse>> getAllByPolicemanId(@PathVariable("id") Long policemanId) throws ExecutionException, InterruptedException {
        CompletableFuture<List<RequestResponse>> future = requestService.getAllByPolicemanId(policemanId);
        List<RequestResponse> requests = future.get();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/police-structure/{id}")
    public ResponseEntity<List<RequestResponse>> getAllByPoliceStructure(@PathVariable("id") Long structureId) throws ExecutionException, InterruptedException {
        CompletableFuture<List<RequestResponse>> future = requestService.getAllByPoliceStructure(structureId);
        List<RequestResponse> requests = future.get();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/police-subunit/{id}")
    public ResponseEntity<List<RequestResponse>> getAllByPoliceSubunit(@PathVariable("id") Long subunitId) throws ExecutionException, InterruptedException {
        CompletableFuture<List<RequestResponse>> future = requestService.getAllByPoliceSubunit(subunitId);
        List<RequestResponse> requests = future.get();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<List<RequestResponse>> getAllByPolicemanName(@RequestParam String name) throws ExecutionException, InterruptedException {
        CompletableFuture<List<RequestResponse>> future = requestService.getAllByPolicemanName(name);
        List<RequestResponse> requests = future.get();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/find/{requestId}")
    public ResponseEntity<RequestResponse> findById(@PathVariable("requestId") Long requestId) throws ExecutionException, InterruptedException {
        CompletableFuture<RequestResponse> future = requestService.findById(requestId);
        RequestResponse request = future.get();
        return ResponseEntity.ok(request);
    }

}
