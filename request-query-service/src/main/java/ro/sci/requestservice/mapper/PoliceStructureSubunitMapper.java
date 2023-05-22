package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.PoliceStructureSubunitResponse;
import ro.sci.requestservice.model.PoliceStructureSubunit;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PoliceStructureSubunitMapper {



    PoliceStructureSubunitResponse map(PoliceStructureSubunit policeStructureSubunit);

    List<PoliceStructureSubunitResponse> map(List<PoliceStructureSubunit> policeStructureSubunits);
}

