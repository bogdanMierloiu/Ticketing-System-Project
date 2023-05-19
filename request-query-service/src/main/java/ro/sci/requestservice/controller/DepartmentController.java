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


@RestController
@RequestMapping("/api/v2/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/find/{policeStructureId}")
    public ResponseEntity<List<DepartmentResponse>> findByPoliceStructure(@PathVariable("policeStructureId") Long policeStructureId) {
        return ResponseEntity.ok(departmentService.getByPoliceStructureId(policeStructureId));
    }


}
