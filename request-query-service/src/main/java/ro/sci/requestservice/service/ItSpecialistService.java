package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.ItSpecialistResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.ItSpecialistMapper;
import ro.sci.requestservice.model.Rank;
import ro.sci.requestservice.repository.ItSpecialistRepo;
import ro.sci.requestservice.repository.RankRepo;

import java.util.List;


@Service
@RequiredArgsConstructor

public class ItSpecialistService {

    private final ItSpecialistRepo itSpecialistRepo;
    private final RankRepo rankRepo;
    private final ItSpecialistMapper itSpecialistMapper;


    public List<ItSpecialistResponse> findAll() {
        return itSpecialistMapper.map(itSpecialistRepo.findAll());
    }

    private Rank getRankById(Long rankId) {
        return rankRepo.findById(rankId).orElseThrow(
                () -> new NotFoundException("The rank with id " + rankId + " not found")
        );
    }


}
