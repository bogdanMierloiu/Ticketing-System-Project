package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.CommitmentResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.CommitmentMapper;
import ro.sci.requestservice.model.Commitment;
import ro.sci.requestservice.repository.CommitmentRepo;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CommitmentService {

    private final CommitmentRepo commitmentRepo;
    private final CommitmentMapper commitmentMapper;

    @Async
    @Transactional
    public CompletableFuture<CommitmentResponse> findByRequest(Long requestId) {
        Commitment commitment = commitmentRepo.findByRequestId(requestId);
        if (commitment == null) {
            throw new NotFoundException("Commitment not found");
        }

        byte[] documentData = commitment.getDocumentData();
        CommitmentResponse commitmentResponse = CommitmentResponse.builder()
                .id(commitment.getId())
                .documentData(documentData)
                .build();
        return CompletableFuture.completedFuture(commitmentResponse);
    }

}

