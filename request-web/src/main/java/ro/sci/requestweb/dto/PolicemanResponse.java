package ro.sci.requestweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PolicemanResponse {

    private Long id;

    private String firstName;

    private String firstNameSecondary;

    private String lastName;

    private String personalNumber;

    private String certificate;

    private LocalDate certificateValidFrom;

    private LocalDate certificateValidUntil;

    private String phoneNumber;

    private String phoneNumberPolice;

    private String email;

    private RankResponse rank;

    private PoliceStructureResponse policeStructure;

    private DepartmentResponse department;
}
