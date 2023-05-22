package ro.sci.requestservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PolicemanRequest {

    private Long id;

    @NotBlank
    private String firstName;

    private String firstNameSecondary;

    @NotBlank
    private String lastName;

    @NotBlank
    private String personalNumber;

    @NotBlank
    private String certificate;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String email;

    @NotBlank
    private Long rankId;

    @NotBlank
    private Long policeStructureId;

    @NotBlank
    private Long departmentId;
}
