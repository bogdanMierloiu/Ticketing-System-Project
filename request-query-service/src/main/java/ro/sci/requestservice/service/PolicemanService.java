package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.PolicemanResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.PolicemanMapper;
import ro.sci.requestservice.model.Department;
import ro.sci.requestservice.model.PoliceStructure;
import ro.sci.requestservice.model.Rank;
import ro.sci.requestservice.repository.DepartmentRepo;
import ro.sci.requestservice.repository.PoliceStructureRepo;
import ro.sci.requestservice.repository.PolicemanRepo;
import ro.sci.requestservice.repository.RankRepo;

import java.util.List;


@Service
@RequiredArgsConstructor

public class PolicemanService {

    private final PolicemanRepo policemanRepo;
    private final RankRepo rankRepo;
    private final PoliceStructureRepo policeStructureRepo;
    private final DepartmentRepo departmentRepo;
    private final PolicemanMapper policemanMapper;


    public List<PolicemanResponse> findAll() {
        return policemanMapper.map(policemanRepo.findAll());
    }

    private Rank getRankById(Long rankId) {
        return rankRepo.findById(rankId).orElseThrow(
                () -> new NotFoundException("The rank with id " + rankId + " not found")
        );
    }

    private PoliceStructure getPoliceStructureById(Long policeStructureId) {
        return policeStructureRepo.findById(policeStructureId).orElseThrow(
                () -> new NotFoundException("The police structure with id " + policeStructureId + " not found")
        );
    }

    private Department getDepartmentById(Long departmentId) {
        return departmentRepo.findById(departmentId).orElseThrow(
                () -> new NotFoundException("The department with id " + departmentId + " not found")
        );
    }


}
