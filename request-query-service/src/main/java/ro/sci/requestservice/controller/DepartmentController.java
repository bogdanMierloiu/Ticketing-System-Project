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
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/v2/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/{subunitId}")
    public ResponseEntity<List<DepartmentResponse>> findBySubunit(@PathVariable("subunitId") Long subunitId) throws ExecutionException, InterruptedException {
        CompletableFuture<List<DepartmentResponse>> future = departmentService.getBySubunitId(subunitId);
        List<DepartmentResponse> departments = future.get();
        return ResponseEntity.ok(departments);
    }


}
