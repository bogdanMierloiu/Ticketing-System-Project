package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.PoliceStructureSubunitResponse;
import ro.sci.requestservice.service.PoliceStructureSubunitService;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("api/v2/police-structure-subunit")
@RequiredArgsConstructor
public class PoliceStructureSubunitController {

    private final PoliceStructureSubunitService policeStructureSubunitService;

    @GetMapping("/all-structures")
    public CompletableFuture<ResponseEntity<List<PoliceStructureSubunitResponse>>> getAllSubunitStructures() {
        CompletableFuture<List<PoliceStructureSubunitResponse>> future = policeStructureSubunitService.getAllSubunitStructures();
        return future.thenApply(ResponseEntity::ok);
    }


    @GetMapping("/find/{policeStructureId}")
    public CompletableFuture<ResponseEntity<List<PoliceStructureSubunitResponse>>> findByPoliceStructure(@PathVariable("policeStructureId") Long policeStructureId) {
        CompletableFuture<List<PoliceStructureSubunitResponse>> future = policeStructureSubunitService.getByPoliceStructure(policeStructureId);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{subunitId}")
    public CompletableFuture<ResponseEntity<PoliceStructureSubunitResponse>> findById(@PathVariable("subunitId") Long subunitId) {
        CompletableFuture<PoliceStructureSubunitResponse> future = policeStructureSubunitService.findById(subunitId);
        return future.thenApply(ResponseEntity::ok);
    }


}
