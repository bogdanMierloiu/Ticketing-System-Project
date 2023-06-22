package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.CommitmentRequest;
import ro.sci.requestservice.model.Commitment;
import ro.sci.requestservice.repository.CommitmentRepo;


@Service
@RequiredArgsConstructor
@Transactional
public class CommitmentService {

    private final CommitmentRepo commitmentRepo;

    public Commitment add(CommitmentRequest commitmentRequest) {
        Commitment commitment = new Commitment();
        commitment.setDocumentData(commitmentRequest.getDocumentData());
        return commitmentRepo.save(commitment);
    }


}
