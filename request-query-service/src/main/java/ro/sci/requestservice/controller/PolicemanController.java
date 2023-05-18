package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.service.PolicemanService;


@RestController
@RequestMapping("/api/v2/policeman")
@RequiredArgsConstructor
public class PolicemanController {

    private final PolicemanService policemanService;


}
