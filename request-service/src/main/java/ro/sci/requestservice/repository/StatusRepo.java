package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sci.requestservice.model.Status;

public interface StatusRepo extends JpaRepository<Status, Long> {
}
