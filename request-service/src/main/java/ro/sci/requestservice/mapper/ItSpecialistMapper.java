package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.ItSpecialistRequest;
import ro.sci.requestservice.dto.ItSpecialistResponse;
import ro.sci.requestservice.model.ItSpecialist;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItSpecialistMapper {

    ItSpecialist map(ItSpecialistRequest itSpecialistRequest);

    ItSpecialistResponse map(ItSpecialist itSpecialist);

    List<ItSpecialistResponse> map(List<ItSpecialist> itSpecialists);
}
