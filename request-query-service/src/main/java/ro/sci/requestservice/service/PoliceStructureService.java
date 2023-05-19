package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.PoliceStructureResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.PoliceStructureMapper;
import ro.sci.requestservice.repository.PoliceStructureRepo;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PoliceStructureService {

    private final PoliceStructureRepo policeStructureRepo;

    private final PoliceStructureMapper policeStructureMapper;

    public List<PoliceStructureResponse> getAllStructures() {
        return policeStructureMapper.map(policeStructureRepo.findAll());
    }

    public PoliceStructureResponse findById(Long id) {
        return policeStructureMapper.map(policeStructureRepo.findById(id).orElseThrow(
                () -> new NotFoundException("The police structure with id " + id + " not found")
        ));
    }


}
