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

    @PatchMapping("/structure-chief-approve/{requestId}")
    public ResponseEntity<String> structureChiefApprove(@PathVariable("requestId") Long requestId) {
        requestService.structureChiefApprove(requestId);
        return ResponseEntity.ok("Approved successfully");
    }
}
