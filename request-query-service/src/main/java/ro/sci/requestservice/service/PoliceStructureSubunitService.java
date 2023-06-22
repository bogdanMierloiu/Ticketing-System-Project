package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.PoliceStructureSubunitResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.PoliceStructureSubunitMapper;
import ro.sci.requestservice.model.PoliceStructureSubunit;
import ro.sci.requestservice.repository.PoliceStructureSubunitRepo;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Transactional
public class PoliceStructureSubunitService {

    private final PoliceStructureSubunitRepo policeStructureSubunitRepo;
    private final PoliceStructureSubunitMapper policeStructureSubunitMapper;

    @Async
    public CompletableFuture<List<PoliceStructureSubunitResponse>> getAllSubunitStructures() {
        List<PoliceStructureSubunit> allSubunitStructures = policeStructureSubunitRepo.findAll();
        allSubunitStructures.sort(Comparator.comparing(this::extractNumberFromStructureName));
        return CompletableFuture.completedFuture(policeStructureSubunitMapper.map(allSubunitStructures));
    }

    @Async
    public CompletableFuture<List<PoliceStructureSubunitResponse>> getByPoliceStructure(Long policeStructureId) {
        List<PoliceStructureSubunit> subunitStructures = policeStructureSubunitRepo.findByPoliceStructureId(policeStructureId);
        return CompletableFuture.completedFuture(policeStructureSubunitMapper.map(subunitStructures));
    }

    @Async
    public CompletableFuture<PoliceStructureSubunitResponse> findById(Long id) {
        PoliceStructureSubunit subunitResponse = policeStructureSubunitRepo.findById(id).orElseThrow(
                () -> new NotFoundException("The police subunit structure with id " + id + " not found")
        );
        return CompletableFuture.completedFuture(policeStructureSubunitMapper.map(subunitResponse));
    }

    private int extractNumberFromStructureName(PoliceStructureSubunit structure) {
        String structureName = structure.getSubunitName();
        String numberString = structureName.replaceAll("[^0-9]", "");
        return numberString.isEmpty() ? 999999 : Integer.parseInt(numberString);
    }


}
