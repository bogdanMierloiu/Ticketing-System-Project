package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.PolicemanRequest;
import ro.sci.requestservice.service.PolicemanService;


@RestController
@RequestMapping("/api/v1/policeman")
@RequiredArgsConstructor
public class PolicemanController {

    private final PolicemanService policemanService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody PolicemanRequest policemanRequest) {
        policemanService.add(policemanRequest);
        return new ResponseEntity<>("Added successfully", HttpStatus.OK);
    }
}
