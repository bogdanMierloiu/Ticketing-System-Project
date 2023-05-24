package ro.sci.requestservice.mapper;

import org.mapstruct.*;
import ro.sci.requestservice.dto.PolicemanRequest;
import ro.sci.requestservice.dto.PolicemanResponse;
import ro.sci.requestservice.model.Policeman;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PolicemanMapper {

    Policeman map(PolicemanRequest policemanRequest);

    PolicemanResponse map(Policeman policeman);

    List<PolicemanResponse> map(List<Policeman> policemen);

    @Mapping(source = "rank.id", target = "rank.id")
    @Mapping(source = "policeStructure.id", target = "policeStructureId")
    @Mapping(source = "policeStructureSubunit.id", target = "policeStructureSubunitId")
    @Mapping(source = "department.id", target = "departmentId")
    @Named("mapPolicemanToResponse")
    PolicemanResponse mapPolicemanToResponse(Policeman policeman);

    Policeman mapRequestToPoliceman(PolicemanRequest policemanRequest);
}
