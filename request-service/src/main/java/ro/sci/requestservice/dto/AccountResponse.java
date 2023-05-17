package ro.sci.requestservice.dto;

import lombok.Builder;
import lombok.Data;
import ro.sci.requestservice.model.Status;

@Data
@Builder
public class AccountResponse {

    private Long id;

    private PolicemanResponse policemanResponse;

    private Status status;

    private String observation;

    private Boolean isApprovedByStructureChief;

    private Boolean isApprovedBySecurityStructure;

    private Boolean isApprovedByITChief;

}
