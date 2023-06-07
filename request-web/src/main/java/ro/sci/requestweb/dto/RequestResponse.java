package ro.sci.requestweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

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

    // Request Structure

    private String requestStructRegNo;

    private LocalDate regDateFromRequestStruct;

    private Boolean isApprovedByStructureChief;

    // Security Structure

    private String securityStructRegNo;

    private LocalDate regDateFromSecurityStruct;

    private Boolean isApprovedBySecurityStructure;

    // IT Structure

    private Long itStructRegNo;

    private LocalDate regDateFromITStruct;

    private Boolean isApprovedByITChief;

}
