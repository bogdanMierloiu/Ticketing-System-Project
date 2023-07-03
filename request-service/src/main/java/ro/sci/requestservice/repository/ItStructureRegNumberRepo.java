package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.sci.requestservice.model.ItStructureRegistrationNumber;

public interface ItStructureRegNumberRepo extends JpaRepository<ItStructureRegistrationNumber, Long> {

    @Query("SELECT n FROM ItStructureRegistrationNumber n ORDER BY n.id DESC LIMIT 1 ")
    public ItStructureRegistrationNumber findLast();


}
