package ro.sci.requestservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItSpecialistRequest {

    private Long id;

    private String firstName;


    private String lastName;


    private Long rankId;

}
