package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.RankResponse;
import ro.sci.requestservice.mapper.RankMapper;
import ro.sci.requestservice.repository.RankRepo;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RankService {

    private final RankRepo rankRepo;
    private final RankMapper rankMapper;


    public List<RankResponse> findAll() {
        return rankMapper.map(rankRepo.findAll());
    }


}
