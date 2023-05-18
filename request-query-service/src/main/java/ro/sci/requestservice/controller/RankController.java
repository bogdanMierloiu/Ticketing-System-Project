package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.service.RankService;


@RestController
@RequestMapping("/api/v2/rank")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;


}
