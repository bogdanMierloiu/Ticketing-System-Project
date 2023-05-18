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
public class AccountRequest {

    private Long id;

    @NotBlank
    private PolicemanRequest policemanRequest;

    @NotBlank
    private Long requestTypeId;

    @NotBlank
    private String requestStructRegNo;

    private String observation;
}
