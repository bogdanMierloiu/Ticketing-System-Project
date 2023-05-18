package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.service.RequestTypeService;


@RestController
@RequestMapping("/api/v2/request-type")
@RequiredArgsConstructor
public class RequestTypeController {

    private final RequestTypeService requestTypeService;


}
