package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sci.requestservice.model.Commitment;

public interface CommitmentRepo extends JpaRepository<Commitment, Long> {
}
