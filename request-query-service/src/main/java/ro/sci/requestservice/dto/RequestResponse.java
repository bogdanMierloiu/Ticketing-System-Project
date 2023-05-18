package ro.sci.requestservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ro.sci.requestservice.model.Status;

@Data
@Builder
public class RequestResponse {

    private Long id;

    @ToString.Exclude
    private PolicemanResponse policemanResponse;

    @ToString.Exclude
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
