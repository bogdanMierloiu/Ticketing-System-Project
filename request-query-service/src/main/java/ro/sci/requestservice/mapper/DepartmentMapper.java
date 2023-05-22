package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.DepartmentResponse;
import ro.sci.requestservice.model.Department;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DepartmentMapper {


    DepartmentResponse map(Department department);

    List<DepartmentResponse> map(List<Department> departments);
}