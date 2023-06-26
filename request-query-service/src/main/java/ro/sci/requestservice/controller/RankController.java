package ro.sci.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.sci.requestservice.dto.RankResponse;
import ro.sci.requestservice.service.RankService;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/v2/rank")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @GetMapping("/all-ranks")
    public CompletableFuture<ResponseEntity<List<RankResponse>>> getAll() {
        CompletableFuture<List<RankResponse>> future = rankService.findAll();
        return future.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/find/{rankId}")
    public CompletableFuture<ResponseEntity<RankResponse>> findById(@PathVariable("rankId") Long rankId) {
        CompletableFuture<RankResponse> future = rankService.getById(rankId);
        return future.thenApply(ResponseEntity::ok);
    }
}
