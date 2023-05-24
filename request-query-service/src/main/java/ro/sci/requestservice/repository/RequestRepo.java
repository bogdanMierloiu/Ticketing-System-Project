package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sci.requestservice.model.Request;

import java.util.List;

public interface RequestRepo extends JpaRepository<Request, Long> {

    @Query("Select r from Request r Order By r.createdAt desc")
    public List<Request> findAllOrderByCreatedAtDesc();

    @Query("Select r from Request r where r.policeman.id = :id")
    public List<Request> findAllByPolicemanId(@Param("id") Long id);


    @Query("SELECT r FROM Request r WHERE LOWER(r.policeman.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')) AND " +
            "LOWER(r.policeman.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')) AND " +
            "LOWER(r.policeman.firstNameSecondary) LIKE LOWER(CONCAT('%', :firstNameSecondary, '%'))")
    public List<Request> findAllByPolicemanName(@Param("lastName") String lastName,
                                                @Param("firstName") String firstName,
                                                @Param("firstNameSecondary") String firstNameSecondary);




}
