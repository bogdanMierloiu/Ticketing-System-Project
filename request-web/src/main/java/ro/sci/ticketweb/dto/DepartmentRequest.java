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
public class DepartmentRequest {

    private Long id;

    @NotBlank
    private String departmentName;

    @NotBlank
    private Long policeStructureId;
}
