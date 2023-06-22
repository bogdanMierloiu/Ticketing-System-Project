package ro.sci.requestweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CommitmentRequest {

    private Long id;

    private String documentName;

    private MultipartFile documentData;
}
