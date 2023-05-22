package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.PolicemanRequest;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.model.Department;
import ro.sci.requestservice.model.PoliceStructure;
import ro.sci.requestservice.model.Policeman;
import ro.sci.requestservice.model.Rank;
import ro.sci.requestservice.repository.DepartmentRepo;
import ro.sci.requestservice.repository.PoliceStructureRepo;
import ro.sci.requestservice.repository.PolicemanRepo;
import ro.sci.requestservice.repository.RankRepo;


@Service
@RequiredArgsConstructor
public class PolicemanService {

    private final PolicemanRepo policemanRepo;
    private final RankRepo rankRepo;
    private final PoliceStructureRepo policeStructureRepo;
    private final DepartmentRepo departmentRepo;

    public Policeman add(PolicemanRequest policemanRequest) {
        Policeman policeman = new Policeman();
        policeman.setFirstName(policemanRequest.getFirstName().toUpperCase());
        policeman.setFirstNameSecondary(policemanRequest.getFirstNameSecondary().toUpperCase());
        policeman.setLastName(policemanRequest.getLastName().toUpperCase());
        policeman.setPersonalNumber(policemanRequest.getPersonalNumber());
        policeman.setCertificate(policemanRequest.getCertificate());
        policeman.setPhoneNumber(policemanRequest.getPhoneNumber());
        policeman.setEmail(policemanRequest.getEmail());
        policeman.setRank(getRankById(policemanRequest.getRankId()));
        policeman.setPoliceStructure(getPoliceStructureById(policemanRequest.getPoliceStructureId()));
        policeman.setDepartment(getDepartmentById(policemanRequest.getDepartmentId()));

        return policemanRepo.save(policeman);
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

    private Department getDepartmentById(Long departmentId){
        return departmentRepo.findById(departmentId).orElseThrow(
                () -> new NotFoundException("The department with id " + departmentId + " not found")
        );
    }
}
