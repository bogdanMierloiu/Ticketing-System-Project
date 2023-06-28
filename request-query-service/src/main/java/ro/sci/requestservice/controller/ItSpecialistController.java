package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.ItSpecialistResponse;
import ro.sci.requestservice.service.ItSpecialistService;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/v2/it-specialist")
@RequiredArgsConstructor
public class ItSpecialistController {

    private final ItSpecialistService itSpecialistService;

    @GetMapping("/all-specialists")
    public CompletableFuture<ResponseEntity<List<ItSpecialistResponse>>> getAll() {
        CompletableFuture<List<ItSpecialistResponse>> future = itSpecialistService.findAll();
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/find/{lastName}")
    public CompletableFuture<ResponseEntity<ItSpecialistResponse>> findByName(@PathVariable("lastName") String lastName) {
        CompletableFuture<ItSpecialistResponse> future = itSpecialistService.findByName(lastName);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/count/all-requests/{specialistId}")
    public CompletableFuture<ResponseEntity<Long>> countAllRequests(@PathVariable("specialistId") Long specialistId) {
        CompletableFuture<Long> future = itSpecialistService.countAllRequests(specialistId);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/count/all-requests-in-progress/{specialistId}")
    public CompletableFuture<ResponseEntity<Long>> findAllInProgressByItSpecialistId(@PathVariable("specialistId") Long specialistId) {
        CompletableFuture<Long> future = itSpecialistService.findAllInProgressByItSpecialistId(specialistId);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/count/all-requests-finalized/{specialistId}")
    public CompletableFuture<ResponseEntity<Long>> findAllFinalizedByItSpecialistId(@PathVariable("specialistId") Long specialistId) {
        CompletableFuture<Long> future = itSpecialistService.findAllFinalizedByItSpecialistId(specialistId);
        return future.thenApply(ResponseEntity::ok);
    }







}
