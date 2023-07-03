package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.ItStructureRegistrationNumberResponse;
import ro.sci.requestservice.model.ItStructureRegistrationNumber;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItStructureRegistrationNumberMapper {

    ItStructureRegistrationNumberResponse map(ItStructureRegistrationNumber itNumber);
}
