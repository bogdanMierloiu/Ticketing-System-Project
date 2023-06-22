package ro.sci.requestweb.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ro.sci.requestweb.dto.CommitmentRequest;
import ro.sci.requestweb.dto.CommitmentRequestToSend;

import java.io.IOException;

@Component
public class CommitmentRequestMapper {
    public CommitmentRequestToSend mapToCommitmentRequestToSend(CommitmentRequest commitmentRequest) {
        MultipartFile documentData = commitmentRequest.getDocumentData();
        byte[] documentBytes = null;
        if (documentData != null) {
            documentBytes = multiPartToByte(documentData);
        }

        return CommitmentRequestToSend.builder()
                .id(commitmentRequest.getId() != null ? commitmentRequest.getId() : null)
                .documentName(commitmentRequest.getDocumentName())
                .documentData(documentBytes)
                .build();
    }

    private byte[] multiPartToByte(MultipartFile file) {
        byte[] documentData = new byte[0];
        try {
            documentData = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documentData;
    }
}
