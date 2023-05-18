package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.RequestTypeReq;
import ro.sci.requestservice.dto.RequestTypeResponse;
import ro.sci.requestservice.service.RequestTypeService;


@RestController
@RequestMapping("/api/v1/request-type")
@RequiredArgsConstructor
public class RequestTypeController {

    private final RequestTypeService requestTypeService;

    @PostMapping
    public ResponseEntity<RequestTypeResponse> add(@RequestBody RequestTypeReq requestTypeReq) {
        return new ResponseEntity<>(requestTypeService.add(requestTypeReq), HttpStatus.OK);
    }
}
