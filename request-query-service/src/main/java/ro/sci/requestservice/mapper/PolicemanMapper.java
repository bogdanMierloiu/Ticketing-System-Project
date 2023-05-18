package ro.sci.requestservice.mapper;

import org.mapstruct.*;
import ro.sci.requestservice.dto.PolicemanResponse;
import ro.sci.requestservice.model.Policeman;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PolicemanMapper {

    PolicemanResponse map(Policeman policeman);

    List<PolicemanResponse> map(List<Policeman> policemen);

    @Mapping(source = "rank.id", target = "rank.id")
    @Mapping(source = "policeStructure.id", target = "policeStructure.id")
    @Mapping(source = "department.id", target = "department.id")
    @Mapping(source = "policeStructure", target = "policeStructure")
    @Mapping(source = "department", target = "department")
    @Named("mapPolicemanToResponse")
    PolicemanResponse mapPolicemanToResponse(Policeman policeman);




}
