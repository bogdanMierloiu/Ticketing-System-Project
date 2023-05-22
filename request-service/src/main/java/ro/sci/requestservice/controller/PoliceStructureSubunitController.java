package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.PoliceStructureSubunitRequest;
import ro.sci.requestservice.service.PoliceStructureSubunitService;


@RestController
@RequestMapping("api/v1/police-structure")
@RequiredArgsConstructor
public class PoliceStructureSubunitController {

    private final PoliceStructureSubunitService policeStructureSubunitService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody PoliceStructureSubunitRequest policeStructureSubunitRequest) {
        policeStructureSubunitService.add(policeStructureSubunitRequest);
        return new ResponseEntity<>("Added successfully", HttpStatus.OK);
    }
}
