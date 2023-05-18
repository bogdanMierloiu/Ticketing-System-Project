package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.PoliceStructureRequest;
import ro.sci.requestservice.dto.PoliceStructureResponse;
import ro.sci.requestservice.model.PoliceStructure;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PoliceStructureMapper {


    PoliceStructure map(PoliceStructureRequest request);

    PoliceStructureResponse map(PoliceStructure policeStructure);

    List<PoliceStructureRequest> map(List<PoliceStructure> policeStructures);
}

