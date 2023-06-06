package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.PoliceStructureRequest;
import ro.sci.requestservice.model.PoliceStructure;
import ro.sci.requestservice.repository.PoliceStructureRepo;


@Service
@RequiredArgsConstructor
@Transactional
public class PoliceStructureService {

    private final PoliceStructureRepo policeStructureRepo;

    public void add(PoliceStructureRequest policeStructureRequest) {
        PoliceStructure policeStructure = new PoliceStructure();
        policeStructure.setStructureName(policeStructureRequest.getStructureName());
        policeStructureRepo.save(policeStructure);
    }
}
