package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.ItSpecialistRequest;
import ro.sci.requestservice.dto.ItSpecialistResponse;
import ro.sci.requestservice.service.ItSpecialistService;


@RestController
@RequestMapping("/api/v1/it-specialist")
@RequiredArgsConstructor
public class ItSpecialistController {

    private final ItSpecialistService itSpecialistService;

    @PostMapping
    public ResponseEntity<ItSpecialistResponse> add(@RequestBody ItSpecialistRequest itSpecialistRequest) {
        return new ResponseEntity<>(itSpecialistService.add(itSpecialistRequest), HttpStatus.OK);
    }
}
