package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.service.RequestService;

import java.util.List;


@RestController
@RequestMapping("/api/v2/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/all-requests")
    public ResponseEntity<List<RequestResponse>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/all-requests-in-progress")
    public ResponseEntity<List<RequestResponse>> findNonFinalizedAndRecentRejectedRequests() {
        return ResponseEntity.ok(requestService.findNonFinalizedAndRecentRejectedRequests());
    }

    @GetMapping("/policeman/{id}")
    public ResponseEntity<List<RequestResponse>> getAllByPolicemanId(@PathVariable("id") Long policemanId) {
        return ResponseEntity.ok(requestService.getAllByPolicemanId(policemanId));
    }

    @GetMapping("/police-structure/{id}")
    public ResponseEntity<List<RequestResponse>> getAllByPoliceStructure(@PathVariable("id") Long structureId) {
        return ResponseEntity.ok(requestService.getAllByPoliceStructure(structureId));
    }

    @GetMapping("/police-subunit/{id}")
    public ResponseEntity<List<RequestResponse>> getAllByPoliceSubunit(@PathVariable("id") Long subunitId) {
        return ResponseEntity.ok(requestService.getAllByPoliceSubunit(subunitId));
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<List<RequestResponse>> getAllByPolicemanName(@RequestParam String name) {
        List<RequestResponse> requests = requestService.getAllByPolicemanName(name);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/find/{requestId}")
    public ResponseEntity<RequestResponse> findById(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok(requestService.findById(requestId));
    }


}
