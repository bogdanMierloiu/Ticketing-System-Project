package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.PoliceStructureResponse;
import ro.sci.requestservice.service.PoliceStructureService;

import java.util.List;


@RestController
@RequestMapping("api/v2/police-structure")
@RequiredArgsConstructor
public class PoliceStructureController {

    private final PoliceStructureService policeStructureService;

    @GetMapping("/all-structures")
    public ResponseEntity<List<PoliceStructureResponse>> getAllStructures() {
        return ResponseEntity.ok(policeStructureService.getAllStructures());
    }


}
