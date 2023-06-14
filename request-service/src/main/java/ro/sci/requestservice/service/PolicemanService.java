package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.PolicemanRequest;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.model.*;
import ro.sci.requestservice.repository.*;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Transactional

public class PolicemanService {

    private final PolicemanRepo policemanRepo;
    private final RankRepo rankRepo;
    private final PoliceStructureRepo policeStructureRepo;
    private final PoliceStructureSubunitRepo policeStructureSubunitRepo;
    private final DepartmentRepo departmentRepo;

    @Async
    public CompletableFuture<Policeman> add(PolicemanRequest policemanRequest) {
        return CompletableFuture.supplyAsync(() -> {
            Policeman policemanFromDB = policemanExists(policemanRequest);
            if (policemanFromDB == null) {
                Policeman policeman = new Policeman();
                policeman.setFirstName(policemanRequest.getFirstName().strip());
                if (policemanRequest.getFirstNameSecondary() != null) {
                    policeman.setFirstNameSecondary(policemanRequest.getFirstNameSecondary().strip());
                }
                policeman.setLastName(policemanRequest.getLastName().strip());
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
            } else {
                checkPolicemanFields(policemanFromDB, policemanRequest);
                return policemanFromDB;
            }
        });
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

    private Policeman policemanExists(PolicemanRequest policemanRequest) {
        return policemanRepo.findByPersonalNumber(policemanRequest.getPersonalNumber());
    }

    @Async
    private void checkPolicemanFields(Policeman policemanFromDB, PolicemanRequest policemanRequest) {
        boolean hasChanges = false;
        if (!(policemanRequest.getCertificate() == null)) {
            if (!Objects.equals(policemanFromDB.getCertificate(), policemanRequest.getCertificate())) {
                policemanFromDB.setCertificate(policemanRequest.getCertificate());
                policemanFromDB.setCertificateValidFrom(policemanRequest.getCertificateValidFrom());
                policemanFromDB.setCertificateValidUntil(policemanRequest.getCertificateValidUntil());
                hasChanges = true;
            }
        }
        if (!Objects.equals(policemanFromDB.getPhoneNumber(), policemanRequest.getPhoneNumber())) {
            policemanFromDB.setPhoneNumber(policemanRequest.getPhoneNumber());
            hasChanges = true;
        }
        if (!Objects.equals(policemanFromDB.getPhoneNumberPolice(), policemanRequest.getPhoneNumberPolice())) {
            policemanFromDB.setPhoneNumberPolice(policemanRequest.getPhoneNumberPolice());
            hasChanges = true;
        }
        if (!Objects.equals(policemanFromDB.getEmail(), policemanRequest.getEmail())) {
            policemanFromDB.setEmail(policemanRequest.getEmail());
            hasChanges = true;
        }

        Rank rank = getRankById(policemanRequest.getRankId());
        if (!Objects.equals(policemanFromDB.getRank(), rank)) {
            policemanFromDB.setRank(rank);
            hasChanges = true;
        }

        PoliceStructure policeStructure = getPoliceStructureById(policemanRequest.getPoliceStructureId());
        if (!Objects.equals(policemanFromDB.getPoliceStructure(), policeStructure)) {
            policemanFromDB.setPoliceStructure(policeStructure);
            hasChanges = true;
        }

        PoliceStructureSubunit policeStructureSubunit = getPoliceStructureSubunitById(policemanRequest.getPoliceStructureSubunitId());
        if (!Objects.equals(policemanFromDB.getPoliceStructureSubunit(), policeStructureSubunit)) {
            policemanFromDB.setPoliceStructureSubunit(policeStructureSubunit);
            hasChanges = true;
        }

        Department department = getDepartmentById(policemanRequest.getDepartmentId());
        if (!Objects.equals(policemanFromDB.getDepartment(), department)) {
            policemanFromDB.setDepartment(department);
            hasChanges = true;
        }

        if (hasChanges) {
            policemanRepo.save(policemanFromDB);
        }
    }


}
