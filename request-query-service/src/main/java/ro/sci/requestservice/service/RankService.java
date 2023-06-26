package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.RankResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.RankMapper;
import ro.sci.requestservice.model.Rank;
import ro.sci.requestservice.repository.RankRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Transactional
public class RankService {

    private final RankRepo rankRepo;
    private final RankMapper rankMapper;


    @Async
    public CompletableFuture<List<RankResponse>> findAll() {
        List<Rank> ranks = rankRepo.findAll();
        return CompletableFuture.completedFuture(rankMapper.map(ranks));
    }

    @Async
    public CompletableFuture<RankResponse> getById(Long rankId) {
        return CompletableFuture.completedFuture(rankMapper.map(rankRepo.findById(rankId)
                .orElseThrow(
                        () -> new NotFoundException("Rank with id " + rankId + " not found!")
                )));
    }


}
