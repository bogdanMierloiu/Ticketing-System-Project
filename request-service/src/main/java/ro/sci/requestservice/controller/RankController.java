package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.RankRequest;
import ro.sci.requestservice.service.RankService;


@RestController
@RequestMapping("/api/v1/rank")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody RankRequest rankRequest) {
        rankService.add(rankRequest);
        return ResponseEntity.ok("Rank: " +
                rankRequest.getRankName() +
                " added successfully");
    }
}
