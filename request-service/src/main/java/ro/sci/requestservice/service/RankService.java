package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.RankRequest;
import ro.sci.requestservice.model.Rank;
import ro.sci.requestservice.repository.RankRepo;


@Service
@RequiredArgsConstructor
@Transactional
public class RankService {

    private final RankRepo rankRepo;


    public void add(RankRequest rankRequest) {
        Rank rank = new Rank();
        rank.setRankName(rankRequest.getRankName());
        rankRepo.save(rank);
    }


}
