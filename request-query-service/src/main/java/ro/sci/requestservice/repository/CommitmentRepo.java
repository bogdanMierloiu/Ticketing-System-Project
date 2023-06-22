package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sci.requestservice.model.Commitment;

import java.util.List;

public interface CommitmentRepo extends JpaRepository<Commitment, Long> {

    @Query("SELECT c FROM Commitment c WHERE c.request.id = :requestId")
    Commitment findByRequestId(@Param("requestId") Long requestId);

    @Query("SELECT c FROM Commitment c WHERE c.id = :id")
    Commitment findByCommitmentId(@Param("id") Long id);

    @Query("SELECT c FROM Commitment c where c.isFromAdmin=true")
    List<Commitment> getAdminCommitments();

}
