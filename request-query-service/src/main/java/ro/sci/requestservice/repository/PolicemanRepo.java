package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sci.requestservice.model.Policeman;

public interface PolicemanRepo extends JpaRepository<Policeman, Long> {
}
