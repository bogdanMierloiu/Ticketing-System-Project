package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.RankResponse;
import ro.sci.requestservice.service.RankService;

import java.util.List;


@RestController
@RequestMapping("/api/v2/rank")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @GetMapping("/all-ranks")
    public ResponseEntity<List<RankResponse>> getAll() {
        return ResponseEntity.ok(rankService.findAll());
    }
}
