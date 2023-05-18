package ro.sci.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sci.requestservice.model.Department;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
}
