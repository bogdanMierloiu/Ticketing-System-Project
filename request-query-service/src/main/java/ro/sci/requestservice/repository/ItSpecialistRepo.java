package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sci.requestservice.model.ItSpecialist;

public interface ItSpecialistRepo extends JpaRepository<ItSpecialist, Long> {
}
