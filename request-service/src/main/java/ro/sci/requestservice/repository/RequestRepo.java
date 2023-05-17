package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sci.requestservice.model.Request;

public interface RequestRepo extends JpaRepository<Request, Long> {
}
