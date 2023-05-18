package ro.sci.ticketweb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PolicemanRequest {

    private Long id;

    @NotBlank
    private String firstName;

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
