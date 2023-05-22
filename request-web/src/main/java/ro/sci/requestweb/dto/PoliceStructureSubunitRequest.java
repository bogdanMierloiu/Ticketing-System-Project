package ro.sci.requestweb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PoliceStructureSubunitRequest {

    private Long id;

    @NotBlank
    private String subunitName;

    @NotBlank
    private Long policeStructureId;



}
