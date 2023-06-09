package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.PoliceStructureSubunitRequest;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.model.PoliceStructure;
import ro.sci.requestservice.model.PoliceStructureSubunit;
import ro.sci.requestservice.repository.PoliceStructureRepo;
import ro.sci.requestservice.repository.PoliceStructureSubunitRepo;


@Service
@RequiredArgsConstructor
@Transactional
public class PoliceStructureSubunitService {

    private final PoliceStructureSubunitRepo policeStructureSubunitRepo;
    private final PoliceStructureRepo policeStructureRepo;

    public void add(PoliceStructureSubunitRequest policeStructureSubunitRequest) {
        PoliceStructureSubunit policeStructureSubunit = new PoliceStructureSubunit();
        policeStructureSubunit.setSubunitName(policeStructureSubunitRequest.getSubunitName());
        policeStructureSubunit.setPoliceStructure(getPoliceStructureById(policeStructureSubunitRequest.getPoliceStructureId()));
        policeStructureSubunitRepo.save(policeStructureSubunit);
    }

    private PoliceStructure getPoliceStructureById(Long policeStructureId) {
        return policeStructureRepo.findById(policeStructureId).orElseThrow(
                () -> new NotFoundException("The police structure with id " + policeStructureId + " not found!")
        );
    }
}
