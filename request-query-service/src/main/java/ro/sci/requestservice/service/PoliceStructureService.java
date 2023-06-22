package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.PoliceStructureResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.PoliceStructureMapper;
import ro.sci.requestservice.model.PoliceStructure;
import ro.sci.requestservice.repository.PoliceStructureRepo;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Transactional
public class PoliceStructureService {

    private final PoliceStructureRepo policeStructureRepo;

    private final PoliceStructureMapper policeStructureMapper;

    @Async
    public CompletableFuture<List<PoliceStructureResponse>> getAllStructures() {
        List<PoliceStructure> allStructures = policeStructureRepo.findAll();
        allStructures.sort(Comparator.comparing(this::extractNumberFromStructureName));
        return CompletableFuture.completedFuture(policeStructureMapper.map(allStructures));
    }

    @Async
    public CompletableFuture<PoliceStructureResponse> findById(Long id) {
        PoliceStructure policeStructureResponse = policeStructureRepo.findById(id).orElseThrow(
                () -> new NotFoundException("The police structure with id " + id + " not found")
        );
        return CompletableFuture.completedFuture(policeStructureMapper.map(policeStructureResponse));
    }

    private int extractNumberFromStructureName(PoliceStructure structure) {
        String structureName = structure.getStructureName();
        String numberString = structureName.replaceAll("[^0-9]", "");
        return numberString.isEmpty() ? 999999 : Integer.parseInt(numberString);
    }


}
