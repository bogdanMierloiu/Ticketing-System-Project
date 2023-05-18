package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.service.PoliceStructureService;


@RestController
@RequestMapping("api/v2/police-structure")
@RequiredArgsConstructor
public class PoliceStructureController {

    private final PoliceStructureService policeStructureService;


}
