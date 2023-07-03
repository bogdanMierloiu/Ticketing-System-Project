package ro.sci.requestservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItStructureRegistrationNumberResponse {

    private Long id;

    private Long number;
}
