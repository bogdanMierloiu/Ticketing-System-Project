package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sci.requestservice.model.PoliceStructure;
import ro.sci.requestservice.model.Policeman;

public interface PoliceStructureRepo extends JpaRepository<PoliceStructure, Long> {
}
