package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sci.requestservice.model.PoliceStructureSubunit;

import java.util.List;

public interface PoliceStructureSubunitRepo extends JpaRepository<PoliceStructureSubunit, Long> {

    @Query("SELECT p FROM PoliceStructureSubunit p where p.policeStructure.id = :id ")
    List<PoliceStructureSubunit> findByPoliceStructureId(@Param("id") Long id);
}
