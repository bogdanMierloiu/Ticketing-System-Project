package ro.sci.requestweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CommitmentRequestToSend {

    private Long id;

    private String documentName;

    private byte[] documentData;
}
