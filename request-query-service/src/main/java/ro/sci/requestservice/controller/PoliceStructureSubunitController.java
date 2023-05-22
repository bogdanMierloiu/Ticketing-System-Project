package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.PoliceStructureSubunitResponse;
import ro.sci.requestservice.service.PoliceStructureSubunitService;

import java.util.List;


@RestController
@RequestMapping("api/v2/police-structure-subunit")
@RequiredArgsConstructor
public class PoliceStructureSubunitController {

    private final PoliceStructureSubunitService policeStructureSubunitService;

    @GetMapping("/all-structures")
    public ResponseEntity<List<PoliceStructureSubunitResponse>> getAllSubunitStructures() {
        return ResponseEntity.ok(policeStructureSubunitService.getAllSubunitStructures());
    }


    @GetMapping("/find/{policeStructureId}")
    public ResponseEntity<List<PoliceStructureSubunitResponse>> findByPoliceStructure(@PathVariable("policeStructureId") Long policeStructureId){
        return ResponseEntity.ok(policeStructureSubunitService.getByPoliceStructure(policeStructureId));
    }

gi
//    @GetMapping("/find/{id}")
//    public ResponseEntity<PoliceStructureSubunitResponse> findById(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(policeStructureSubunitService.findById(id));
//    }


}
