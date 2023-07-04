package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestservice.dto.ItSpecialistRequest;
import ro.sci.requestservice.service.ItSpecialistService;


@RestController
@RequestMapping("/api/v1/it-specialist")
@RequiredArgsConstructor
public class ItSpecialistController {

    private final ItSpecialistService itSpecialistService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ItSpecialistRequest itSpecialistRequest) {
        try {
            itSpecialistService.add(itSpecialistRequest);
            return ResponseEntity.ok("Specialist: " +
                    itSpecialistRequest.getLastName() + " " +
                    itSpecialistRequest.getFirstName() +
                    " added successfully !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ItSpecialistRequest itSpecialistRequest) {
        try {
            itSpecialistService.update(itSpecialistRequest);
            return ResponseEntity.ok("Specialist updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long specialistId) {
        try {
            itSpecialistService.delete(specialistId);
            return ResponseEntity.ok("Specialist deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
