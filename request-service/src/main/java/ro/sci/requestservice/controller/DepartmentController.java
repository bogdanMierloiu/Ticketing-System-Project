package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.DepartmentRequest;
import ro.sci.requestservice.service.DepartmentService;


@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody DepartmentRequest departmentRequest) {
        departmentService.add(departmentRequest);
        return ResponseEntity.ok("Department: " + departmentRequest.getDepartmentName() + " added successfully !");
    }
}
