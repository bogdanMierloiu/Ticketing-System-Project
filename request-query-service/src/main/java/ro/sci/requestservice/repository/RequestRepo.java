package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.sci.requestservice.model.Request;

import java.util.List;

public interface RequestRepo extends JpaRepository<Request, Long> {

    @Query("Select r from Request r Order By r.createdAt desc")
    public List<Request> findAllOrderByCreatedAtDesc();
}
