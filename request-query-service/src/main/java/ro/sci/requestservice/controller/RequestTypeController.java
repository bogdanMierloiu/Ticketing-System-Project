package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.RequestTypeResponse;
import ro.sci.requestservice.service.RequestTypeService;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/v2/request-type")
@RequiredArgsConstructor
public class RequestTypeController {

    private final RequestTypeService requestTypeService;

    @GetMapping("/all-request-types")
    public CompletableFuture<ResponseEntity<List<RequestTypeResponse>>> getAll() {
        CompletableFuture<List<RequestTypeResponse>> future = requestTypeService.findAll();
        return future.thenApply(ResponseEntity::ok);
    }
}
