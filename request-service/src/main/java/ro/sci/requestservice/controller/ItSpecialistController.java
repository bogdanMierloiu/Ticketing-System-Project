package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.ItSpecialistRequest;
import ro.sci.requestservice.service.ItSpecialistService;


@RestController
@RequestMapping("/api/v1/it-specialist")
@RequiredArgsConstructor
public class ItSpecialistController {

    private final ItSpecialistService itSpecialistService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ItSpecialistRequest itSpecialistRequest) {
        itSpecialistService.add(itSpecialistRequest);
        return ResponseEntity.ok("Specialist: " +
                itSpecialistRequest.getLastName() + " " +
                itSpecialistRequest.getLastName() +
                " added successfully !");
    }
}
