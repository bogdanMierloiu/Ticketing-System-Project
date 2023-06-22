package ro.sci.requestweb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

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

    @NotBlank
    private LocalDate regDateFromRequestStruct;

    private String observation;

    private MultipartFile documentData;


}
