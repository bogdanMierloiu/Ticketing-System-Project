package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sci.requestservice.model.ItSpecialist;

public interface ItSpecialistRepo extends JpaRepository<ItSpecialist, Long> {


    @Query("SELECT s FROM ItSpecialist s where s.lastName = :name")
    ItSpecialist findByName(@Param("name") String lastName);
}
