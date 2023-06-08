package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.PoliceStructureResponse;
import ro.sci.requestservice.service.PoliceStructureService;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("api/v2/police-structure")
@RequiredArgsConstructor
public class PoliceStructureController {

    private final PoliceStructureService policeStructureService;

    @GetMapping("/all-structures")
    public CompletableFuture<ResponseEntity<List<PoliceStructureResponse>>> getAllStructures() {
        CompletableFuture<List<PoliceStructureResponse>> future = policeStructureService.getAllStructures();
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/find/{id}")
    public CompletableFuture<ResponseEntity<PoliceStructureResponse>> findById(@PathVariable("id") Long id) {
        CompletableFuture<PoliceStructureResponse> future = policeStructureService.findById(id);
        return future.thenApply(ResponseEntity::ok);
    }


}
