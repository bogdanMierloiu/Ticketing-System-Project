package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.PolicemanRequest;


@RestController
@RequestMapping("/api/v1/policeman")
@RequiredArgsConstructor
public class PolicemanController {

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody PolicemanRequest policemanRequest) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("Adding a new policeman is not allowed in this way!");
    }
}
