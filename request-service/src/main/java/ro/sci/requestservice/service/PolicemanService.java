package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.PolicemanRequest;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.model.*;
import ro.sci.requestservice.repository.*;


@Service
@RequiredArgsConstructor
public class PolicemanService {

    private final PolicemanRepo policemanRepo;
    private final RankRepo rankRepo;
    private final PoliceStructureRepo policeStructureRepo;
    private final PoliceStructureSubunitRepo policeStructureSubunitRepo;
    private final DepartmentRepo departmentRepo;

    public Policeman add(PolicemanRequest policemanRequest) {
        Policeman policeman = new Policeman();
        policeman.setFirstName(policemanRequest.getFirstName());
        policeman.setFirstNameSecondary(policemanRequest.getFirstNameSecondary());
        policeman.setLastName(policemanRequest.getLastName());
        policeman.setPersonalNumber(policemanRequest.getPersonalNumber());
        policeman.setCertificate(policemanRequest.getCertificate());
        policeman.setCertificateValidFrom(policemanRequest.getCertificateValidFrom());
        policeman.setCertificateValidUntil(policemanRequest.getCertificateValidUntil());
        policeman.setPhoneNumber(policemanRequest.getPhoneNumber());
        policeman.setPhoneNumberPolice(policemanRequest.getPhoneNumberPolice());
        policeman.setEmail(policemanRequest.getEmail());
        policeman.setRank(getRankById(policemanRequest.getRankId()));
        policeman.setPoliceStructure(getPoliceStructureById(policemanRequest.getPoliceStructureId()));
        policeman.setPoliceStructureSubunit(getPoliceStructureSubunitById(policemanRequest.getPoliceStructureSubunitId()));
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

    private PoliceStructureSubunit getPoliceStructureSubunitById(Long policeStructureSubunitId) {
        return policeStructureSubunitRepo.findById(policeStructureSubunitId).orElseThrow(
                () -> new NotFoundException("The police structure subunit with id " + policeStructureSubunitId + " not found!")
        );
    }

    private Department getDepartmentById(Long departmentId) {
        return departmentRepo.findById(departmentId).orElseThrow(
                () -> new NotFoundException("The department with id " + departmentId + " not found")
        );
    }


}
