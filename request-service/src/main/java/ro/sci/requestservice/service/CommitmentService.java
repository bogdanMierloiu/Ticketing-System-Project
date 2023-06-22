package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.CommitmentRequest;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.model.Commitment;
import ro.sci.requestservice.repository.CommitmentRepo;


@Service
@RequiredArgsConstructor
@Transactional
public class CommitmentService {

    private final CommitmentRepo commitmentRepo;

    public Commitment add(CommitmentRequest commitmentRequest) {
        Commitment commitment = new Commitment();
        commitment.setDocumentName(commitmentRequest.getDocumentName() != null ? commitmentRequest.getDocumentName() : "");
        commitment.setDocumentData(commitmentRequest.getDocumentData());
        commitment.setIsFromAdmin(true);
        return commitmentRepo.save(commitment);
    }

    public void update(CommitmentRequest commitmentRequest) {
        Commitment commitment = commitmentRepo.findById(commitmentRequest.getId()).orElseThrow(
                () -> new NotFoundException("The commitment with specified id was not found!")
        );
        commitment.setDocumentName(commitmentRequest.getDocumentName() != null ? commitmentRequest.getDocumentName() : commitment.getDocumentName());
        commitment.setDocumentData(commitmentRequest.getDocumentData() != null ? commitmentRequest.getDocumentData() : commitment.getDocumentData());
        commitmentRepo.save(commitment);
    }


}
