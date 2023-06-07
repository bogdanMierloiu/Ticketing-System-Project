package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.ItSpecialistRequest;
import ro.sci.requestservice.dto.ItSpecialistResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.ItSpecialistMapper;
import ro.sci.requestservice.model.ItSpecialist;
import ro.sci.requestservice.model.Rank;
import ro.sci.requestservice.repository.ItSpecialistRepo;
import ro.sci.requestservice.repository.RankRepo;


@Service
@RequiredArgsConstructor
@Transactional

public class ItSpecialistService {

    private final ItSpecialistRepo itSpecialistRepo;
    private final RankRepo rankRepo;
    private final ItSpecialistMapper itSpecialistMapper;


    public ItSpecialistResponse add(ItSpecialistRequest specialistRequest) {
        ItSpecialist itSpecialist = new ItSpecialist();
        itSpecialist.setFirstName(specialistRequest.getFirstName());
        itSpecialist.setLastName(specialistRequest.getLastName());
        itSpecialist.setRank(getRankById(specialistRequest.getRankId()));
        return itSpecialistMapper.map(itSpecialistRepo.save(itSpecialist));

    }


    private Rank getRankById(Long rankId) {
        return rankRepo.findById(rankId).orElseThrow(
                () -> new NotFoundException("The rank with id " + rankId + " not found")
        );
    }


}
