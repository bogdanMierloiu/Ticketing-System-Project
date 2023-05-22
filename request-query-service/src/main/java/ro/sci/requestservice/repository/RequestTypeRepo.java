package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sci.requestservice.model.RequestType;

public interface RequestTypeRepo extends JpaRepository<RequestType, Long> {


}
