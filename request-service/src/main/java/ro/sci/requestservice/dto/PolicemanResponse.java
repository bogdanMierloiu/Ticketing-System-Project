package ro.sci.requestservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PolicemanResponse {

    private Long id;


    private String firstName;


    private String lastName;


    private String personalNumber;


    private String certificate;


    private String phoneNumber;


    private String email;


    private Long rankId;


    private Long policeStructureId;


    private Long departmentId;
}
