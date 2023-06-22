package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.sci.requestservice.model.Commitment;

import java.util.List;

public interface CommitmentRepo extends JpaRepository<Commitment, Long> {



}
