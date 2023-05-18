package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.PoliceStructureRequest;
import ro.sci.requestservice.service.PoliceStructureService;


@RestController
@RequestMapping("api/v1/police-structure")
@RequiredArgsConstructor
public class PoliceStructureController {

    private final PoliceStructureService policeStructureService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody PoliceStructureRequest policeStructureRequest) {
        policeStructureService.add(policeStructureRequest);
        return new ResponseEntity<>("Added successfully", HttpStatus.OK);
    }
}
