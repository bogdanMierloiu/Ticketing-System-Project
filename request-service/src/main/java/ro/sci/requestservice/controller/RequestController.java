package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.service.RequestService;


@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<RequestResponse> add(@RequestBody AccountRequest accountRequest) {
        return new ResponseEntity<>(requestService.add(accountRequest), HttpStatus.OK);
    }

    // POLICE STRUCTURE

    @PatchMapping("/structure-chief-approve/{requestId}")
    public ResponseEntity<String> structureChiefApprove(@PathVariable("requestId") Long requestId) {
        requestService.structureChiefApprove(requestId);
        return ResponseEntity.ok("Approved successfully");
    }

    @PutMapping("/structure-chief-reject/{requestId}")
    public ResponseEntity<String> structureChiefReject(@PathVariable("requestId") Long requestId, @RequestParam("observation") String observation) {
        requestService.structureChiefReject(requestId, observation);
        return ResponseEntity.ok("Rejected successfully");
    }

    // SECURITY STRUCTURE
    @PatchMapping("/security-approve/{requestId}")
    public ResponseEntity<String> securityApprove(@PathVariable("requestId") Long requestId) {
        requestService.securityStructureApprove(requestId);
        return ResponseEntity.ok("Approved successfully");
    }

    @PutMapping("/security-reject/{requestId}")
    public ResponseEntity<String> securityReject(@PathVariable("requestId") Long requestId, @RequestParam String observation) {
        requestService.securityStructureReject(requestId, observation);
        return ResponseEntity.ok("Rejected successfully");
    }

    // SPECIALISTS

    @PatchMapping("/assign-specialist/{requestId}/{itSpecialistId}")
    public ResponseEntity<String> assignSpecialist(@PathVariable("requestId") Long requestId, @PathVariable("itSpecialistId") Long specialistId) {
        requestService.assignSpecialist(requestId, specialistId);
        return ResponseEntity.ok("Assigned successfully");
    }

}
