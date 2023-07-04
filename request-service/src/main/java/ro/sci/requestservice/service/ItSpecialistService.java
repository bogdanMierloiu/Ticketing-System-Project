package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.ItSpecialistRequest;
import ro.sci.requestservice.exception.NotFoundException;
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


    public void add(ItSpecialistRequest specialistRequest) throws NotFoundException {
        ItSpecialist itSpecialist = new ItSpecialist();
        itSpecialist.setFirstName(specialistRequest.getFirstName());
        itSpecialist.setLastName(specialistRequest.getLastName());
        itSpecialist.setRank(getRankById(specialistRequest.getRankId()));
        itSpecialistRepo.save(itSpecialist);
    }

    public void update(ItSpecialistRequest specialistRequest) throws NotFoundException {
        ItSpecialist itSpecialistToUpdate = itSpecialistRepo.findById(specialistRequest.getId()).orElseThrow(
                () -> new NotFoundException(String.format("The specialist with  id %d not found", specialistRequest.getId())));
        if (specialistRequest.getRankId() != null) {
            Rank rank = getRankById(specialistRequest.getRankId());
            itSpecialistToUpdate.setRank(rank);
        }
        itSpecialistToUpdate.setFirstName(specialistRequest.getFirstName() != null ? specialistRequest.getFirstName() : itSpecialistToUpdate.getFirstName());
        itSpecialistToUpdate.setLastName(specialistRequest.getLastName() != null ? specialistRequest.getLastName() : itSpecialistToUpdate.getLastName());

        itSpecialistRepo.save(itSpecialistToUpdate);
    }

    public void delete(Long specialistId) throws NotFoundException {
        ItSpecialist itSpecialist = itSpecialistRepo.findById(specialistId).orElseThrow(
                () -> new NotFoundException(String.format("The specialist with  id %d not found", specialistId)));
        itSpecialistRepo.delete(itSpecialist);
    }


    private Rank getRankById(Long rankId) {
        return rankRepo.findById(rankId).orElseThrow(
                () -> new NotFoundException("The rank with id " + rankId + " not found")
        );
    }


}
