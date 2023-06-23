package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.DepartmentResponse;
import ro.sci.requestservice.service.DepartmentService;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/v2/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/{subunitId}")
    public CompletableFuture<ResponseEntity<List<DepartmentResponse>>> findBySubunit(@PathVariable("subunitId") Long subunitId) {
        CompletableFuture<List<DepartmentResponse>> future = departmentService.getBySubunitId(subunitId);
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/find/{departmentId}")
    public CompletableFuture<ResponseEntity<DepartmentResponse>> findById(@PathVariable("departmentId") Long departmentId) {
        CompletableFuture<DepartmentResponse> future = departmentService.getById(departmentId);
        return future.thenApply(ResponseEntity::ok);
    }

}
