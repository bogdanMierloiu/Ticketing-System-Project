package ro.sci.requestservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AccountRequest {

    private Long id;

    @NotBlank
    private PolicemanRequest policemanRequest;

    @NotBlank
    private Long requestTypeId;

    @NotBlank
    private String requestStructRegNo;

    @NotBlank
    private LocalDate regDateFromRequestStruct;

    private String observation;


}
