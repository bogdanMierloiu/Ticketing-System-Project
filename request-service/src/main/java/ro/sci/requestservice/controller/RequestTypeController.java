package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestservice.dto.RequestTypeReq;
import ro.sci.requestservice.service.RequestTypeService;


@RestController
@RequestMapping("/api/v1/request-type")
@RequiredArgsConstructor
public class RequestTypeController {

    private final RequestTypeService requestTypeService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody RequestTypeReq requestTypeReq) {
        requestTypeService.add(requestTypeReq);
        return ResponseEntity.ok("Request type: " + requestTypeReq.getRequestName() + " added successfully!");
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody RequestTypeReq requestTypeReq) {
        try {
            requestTypeService.update(requestTypeReq);
            return ResponseEntity.ok("Specialist updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long requestTypeId) {
        try {
            requestTypeService.delete(requestTypeId);
            return ResponseEntity.ok("Specialist deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
