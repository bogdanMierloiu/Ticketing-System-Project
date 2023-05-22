package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sci.requestservice.model.PoliceStructureSubunit;

import java.util.List;

public interface PoliceStructureSubunitRepo extends JpaRepository<PoliceStructureSubunit, Long> {


    List<PoliceStructureSubunit> findByPoliceStructureId(Long id);
}
