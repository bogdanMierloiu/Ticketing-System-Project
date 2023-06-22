package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.exception.AlreadyHaveThisRequestException;
import ro.sci.requestservice.exception.UnsupportedOperationException;
import ro.sci.requestservice.service.RequestService;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> add(@RequestBody AccountRequest accountRequest) {
        return requestService.add(accountRequest)
                .thenApply(result -> {
                    if (result.getSuccess()) {
                        return ResponseEntity.ok("Request for " + composeResponseForAddingRequest(accountRequest));
                    } else if (result.getErrorMessage().equals("Already have this request type in progress!")) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getErrorMessage());
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A aparut o eroare la procesarea cererii!");
                    }
                })
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof AlreadyHaveThisRequestException) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getCause().getMessage());
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A aparut o eroare la procesarea cererii!");
                    }
                });
    }


    // POLICE STRUCTURE

    @PatchMapping("/structure-chief-approve/{requestId}")
    public ResponseEntity<String> structureChiefApprove(@PathVariable("requestId") Long requestId) {
        requestService.structureChiefApprove(requestId);
        return ResponseEntity.ok("Approved successfully by structure chief!");
    }

    @PutMapping("/structure-chief-reject/{requestId}")
    public ResponseEntity<String> structureChiefReject(@PathVariable("requestId") Long requestId, @RequestParam("observation") String observation) {
        requestService.structureChiefReject(requestId, observation);
        return ResponseEntity.ok("Rejected successfully by structure chief because: " + observation);
    }

    // SECURITY STRUCTURE
    @PatchMapping("/security-approve/{requestId}")
    public ResponseEntity<?> securityApprove(@PathVariable("requestId") Long requestId) {
        try {
            requestService.securityStructureApprove(requestId);
            return ResponseEntity.ok("Approved successfully by security structure!");
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The request is not " +
                    "approved by chief of police structure ");
        }
    }

    @PutMapping("/security-reject/{requestId}")
    public ResponseEntity<?> securityReject(@PathVariable("requestId") Long requestId, @RequestParam String observation) {
        try {
            requestService.securityStructureReject(requestId, observation);
            return ResponseEntity.ok("Rejected successfully by security structure!");
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The request is not " +
                    "approved by chief of police structure!");
        }
    }


    // IT STRUCTURE

    @PutMapping("/it-approve/{requestId}/{itSpecialistId}")
    public ResponseEntity<String> itApproveAssign(@PathVariable("requestId") Long requestId,
                                                  @PathVariable("itSpecialistId") Long itSpecialistId) {
        try {
            requestService.assignSpecialist(requestId, itSpecialistId);
            return ResponseEntity.ok("Request approved and assigned successfully in SCI");
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Solicitarea nu este aprobata de structura de securitate");
        }

    }

    @PutMapping("/it-reject/{requestId}")
    public ResponseEntity<String> itReject(@PathVariable("requestId") Long requestId,
                                           @RequestParam String observation) {
        requestService.itReject(requestId, observation);
        return ResponseEntity.ok("Request rejected successfully by SCI");
    }


    // FINALIZE

    @PatchMapping("/finalize/{requestId}")
    public ResponseEntity<?> finalizeRequest(@PathVariable("requestId") Long requestId) {
        try {
            requestService.finalize(requestId);
            return ResponseEntity.ok("Request finalized successfully");
        } catch (UnsupportedOperationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }


    //UTILS

    private String composeResponseForAddingRequest(AccountRequest accountRequest) {
        return accountRequest.getPolicemanRequest().getLastName() + " " + accountRequest.getPolicemanRequest().getFirstName() +
                " added successfully";
    }
}
