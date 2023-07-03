package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.model.ItStructureRegistrationNumber;
import ro.sci.requestservice.repository.ItStructureRegNumberRepo;

@Service
@RequiredArgsConstructor
@Transactional
public class ItStructureRegistrationNumberService {

    private final ItStructureRegNumberRepo itStructureRegNumberRepo;

    public synchronized ItStructureRegistrationNumber getNextEntry() {
        ItStructureRegistrationNumber lastEntry = itStructureRegNumberRepo.findLast();
        Long lastNumber = lastEntry.getNumber();
        ItStructureRegistrationNumber nextEntry = new ItStructureRegistrationNumber();
        nextEntry.setNumber(lastNumber + 1);
        return itStructureRegNumberRepo.save(nextEntry);
    }
}
