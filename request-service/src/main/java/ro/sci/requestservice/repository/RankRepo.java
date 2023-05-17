package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sci.requestservice.model.Rank;

public interface RankRepo extends JpaRepository<Rank, Long> {
}
