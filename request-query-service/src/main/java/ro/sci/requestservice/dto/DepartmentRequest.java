package ro.sci.requestservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentRequest {

    private Long id;

    @NotBlank
    private String departmentName;

    @NotBlank
    private Long policeStructureId;
}
