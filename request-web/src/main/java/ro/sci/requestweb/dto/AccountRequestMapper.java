package ro.sci.requestweb.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Component
public class AccountRequestMapper {
    public AccountRequestToSend mapToAccountRequestToSend(AccountRequest accountRequest) {
        MultipartFile documentData = accountRequest.getDocumentData();
        byte[] documentBytes = null;
        if(documentData != null){
            documentBytes = multiPartToByte(documentData);
        }

        CommitmentRequest commitmentRequest = CommitmentRequest.builder()
                .documentData(documentBytes)
                .build();


        return AccountRequestToSend.builder()
                .id(accountRequest.getId())
                .policemanRequest(accountRequest.getPolicemanRequest())
                .requestTypeId(accountRequest.getRequestTypeId())
                .requestStructRegNo(accountRequest.getRequestStructRegNo())
                .regDateFromRequestStruct(accountRequest.getRegDateFromRequestStruct())
                .observation(accountRequest.getObservation())
                .commitmentRequest(commitmentRequest)
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
