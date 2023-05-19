package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.PolicemanResponse;
import ro.sci.requestservice.service.PolicemanService;

import java.util.List;


@RestController
@RequestMapping("/api/v2/policeman")
@RequiredArgsConstructor
public class PolicemanController {

    private final PolicemanService policemanService;

    @GetMapping("/all-policemen")
    public ResponseEntity<List<PolicemanResponse>> findAll(){
        return ResponseEntity.ok(policemanService.findAll());
    }

}
