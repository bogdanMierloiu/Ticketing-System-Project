package ro.sci.requestweb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PolicemanRequest {

    private Long id;

    @NotBlank
    private String firstName; //

    private String firstNameSecondary; //

    @NotBlank
    private String lastName;  //

    @NotBlank
    private String personalNumber; //

    @NotBlank
    private String certificate; //

    @NotBlank
    private LocalDate certificateValidFrom; //

    @NotBlank
    private LocalDate certificateValidUntil;   //

    private String phoneNumber;  //

    @NotBlank
    private String phoneNumberPolice;  //

    @NotBlank
    private String email; //

    @NotBlank
    private Long rankId;

    @NotBlank
    private Long policeStructureId;

    @NotBlank
    private Long policeStructureSubunitId;

    @NotBlank
    private Long departmentId;
}
