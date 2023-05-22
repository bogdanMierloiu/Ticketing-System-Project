package ro.sci.requestweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RequestResponse {

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
