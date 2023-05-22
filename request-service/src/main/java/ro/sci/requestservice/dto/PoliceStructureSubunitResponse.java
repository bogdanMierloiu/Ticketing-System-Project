package ro.sci.requestservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PoliceStructureSubunitResponse {

    private Long id;

    private String subunitName;

    private Long policeStructureId;



}
