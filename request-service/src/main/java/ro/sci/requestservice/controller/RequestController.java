package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.exception.AlreadyHaveThisRequestException;
import ro.sci.requestservice.exception.UnsupportedOperationException;
import ro.sci.requestservice.service.RequestService;


@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody AccountRequest accountRequest) {
        try {
            return new ResponseEntity<>(requestService.add(accountRequest), HttpStatus.OK);
        } catch (AlreadyHaveThisRequestException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already have this request type in progress!");
        }
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
    public ResponseEntity<?> securityApprove(@PathVariable("requestId") Long requestId) {
        try {
            requestService.securityStructureApprove(requestId);
            return ResponseEntity.ok("Approved successfully");
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The request is not " +
                    "approved by chief of police structure ");
        }
    }

    @PutMapping("/security-reject/{requestId}")
    public ResponseEntity<?> securityReject(@PathVariable("requestId") Long requestId, @RequestParam String observation) {
        try {
            requestService.securityStructureReject(requestId, observation);
            return ResponseEntity.ok("Rejected successfully");
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The request is not " +
                    "approved by chief of police structure ");
        }
    }


    // IT STRUCTURE

    @PutMapping("/it-approve/{requestId}/{itSpecialistId}")
    public ResponseEntity<String> itApproveAssign(@PathVariable("requestId") Long requestId,
                                                  @PathVariable("itSpecialistId") Long itSpecialistId) {
        try {
            requestService.assignSpecialist(requestId, itSpecialistId);
            return ResponseEntity.ok("Assigned successfully");
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Solicitarea nu este aprobata de structura de securitate");
        }

    }

    @PutMapping("/it-reject/{requestId}")
    public ResponseEntity<String> itReject(@PathVariable("requestId") Long requestId,
                                           @RequestParam String observation) {
        requestService.itReject(requestId, observation);
        return ResponseEntity.ok("Rejected successfully");
    }


    // SPECIALISTS

    @PatchMapping("/assign-specialist/{requestId}/{itSpecialistId}")
    public ResponseEntity<String> assignSpecialist(@PathVariable("requestId") Long requestId, @PathVariable("itSpecialistId") Long specialistId) {
        requestService.assignSpecialist(requestId, specialistId);
        return ResponseEntity.ok("Assigned successfully");
    }

    // FINALIZE

    @PatchMapping("/finalize/{requestId}")
    public ResponseEntity<?> finalizeRequest(@PathVariable("requestId") Long requestId) {
        try {
            requestService.finalize(requestId);
            return ResponseEntity.ok("Finalized successfully");
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

}
