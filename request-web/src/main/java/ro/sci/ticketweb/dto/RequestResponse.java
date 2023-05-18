package ro.sci.ticketweb.dto;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
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
