package ro.sci.requestservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommitmentRequest {

    private Long id;


    private byte[] documentData;
}
