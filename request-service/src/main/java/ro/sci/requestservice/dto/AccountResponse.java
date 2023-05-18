package ro.sci.requestservice.dto;

import lombok.Builder;
import lombok.Data;
import ro.sci.requestservice.model.Status;

@Data
@Builder
public class AccountResponse {

    private Long id;

    private PolicemanResponse policemanResponse;

    private RequestTypeResponse requestTypeResponse;

    private Status status;

    private String observation;

    private String requestStructRegNo;

    private Boolean isApprovedByStructureChief;

    private String securityStructRegNo;

    private Boolean isApprovedBySecurityStructure;

    private String itStructRegNo;

    private Boolean isApprovedByITChief;

}
