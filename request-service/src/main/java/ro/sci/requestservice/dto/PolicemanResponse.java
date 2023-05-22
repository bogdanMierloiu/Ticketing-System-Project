package ro.sci.requestservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PolicemanResponse {

    private Long id;


    private String firstName;

    private String firstNameSecondary;


    private String lastName;


    private String personalNumber;


    private String certificate;

    private LocalDateTime certificateValidFrom;

    private LocalDateTime certificateValidUntil;

    private String phoneNumber;


    private String email;


    private RankResponse rank;


    private Long policeStructureId;


    private Long departmentId;
}
