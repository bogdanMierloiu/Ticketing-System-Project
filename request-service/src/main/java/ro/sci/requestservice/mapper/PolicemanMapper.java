package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
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
}
