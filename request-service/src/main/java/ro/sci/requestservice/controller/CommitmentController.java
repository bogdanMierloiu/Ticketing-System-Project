package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestservice.dto.CommitmentRequest;
import ro.sci.requestservice.service.CommitmentService;


@RestController
@RequestMapping("/api/v1/commitment")
@RequiredArgsConstructor
public class CommitmentController {

    private final CommitmentService commitmentService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CommitmentRequest commitmentRequest) {
        commitmentService.add(commitmentRequest);
        return ResponseEntity.ok("Commitment: " + commitmentRequest.getDocumentName() + " added successfully !");
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody CommitmentRequest commitmentRequest) {
        commitmentService.update(commitmentRequest);
        return ResponseEntity.ok("Commitment: " + commitmentRequest.getDocumentName() + " updated successfully !");
    }
}
