package ro.sci.ticketweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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

    private String phoneNumber;

    private String email;

    private RankResponse rank;

    private PoliceStructureResponse policeStructure;

    private DepartmentResponse department;
}
