package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.dto.AccountResponse;
import ro.sci.requestservice.service.RequestService;


@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<AccountResponse> add(@RequestBody AccountRequest accountRequest) {
        return new ResponseEntity<>(requestService.add(accountRequest), HttpStatus.OK);
    }
}
