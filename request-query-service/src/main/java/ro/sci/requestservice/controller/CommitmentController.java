package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.CommitmentResponse;
import ro.sci.requestservice.service.CommitmentService;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/v2/commitment")
@RequiredArgsConstructor
public class CommitmentController {

    private final CommitmentService commitmentService;

    @GetMapping("/find-by-request/{requestId}")
    public CompletableFuture<ResponseEntity<CommitmentResponse>> getByRequest(@PathVariable("requestId") Long requestId) {
        CompletableFuture<CommitmentResponse> future = commitmentService.findByRequest(requestId);
        return future.thenApply(ResponseEntity::ok);
    }
}
