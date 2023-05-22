package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.PoliceStructureSubunitResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.PoliceStructureSubunitMapper;
import ro.sci.requestservice.model.PoliceStructureSubunit;
import ro.sci.requestservice.repository.PoliceStructureSubunitRepo;

import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PoliceStructureSubunitService {

    private final PoliceStructureSubunitRepo policeStructureSubunitRepo;

    private final PoliceStructureSubunitMapper policeStructureSubunitMapper;

    public List<PoliceStructureSubunitResponse> getAllSubunitStructures() {
        List<PoliceStructureSubunit> allSubunitStructures = policeStructureSubunitRepo.findAll();
        allSubunitStructures.sort(Comparator.comparing(this::extractNumberFromStructureName));
        return policeStructureSubunitMapper.map(allSubunitStructures);
    }

    public List<PoliceStructureSubunitResponse> getByPoliceStructure(Long policeStructureId) {
        return policeStructureSubunitMapper.map(policeStructureSubunitRepo.findByPoliceStructureId(policeStructureId));
    }

    public PoliceStructureSubunitResponse findById(Long id) {
        return policeStructureSubunitMapper.map(policeStructureSubunitRepo.findById(id).orElseThrow(
                () -> new NotFoundException("The police subunit structure with id " + id + " not found")
        ));
    }

    private int extractNumberFromStructureName(PoliceStructureSubunit structure) {
        String structureName = structure.getSubunitName();
        String numberString = structureName.replaceAll("[^0-9]", "");
        return numberString.isEmpty() ? 999999 : Integer.parseInt(numberString);
    }


}
