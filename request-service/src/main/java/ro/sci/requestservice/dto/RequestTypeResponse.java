package ro.sci.requestservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestTypeResponse {

    private Long id;


    private String requestName;


}
