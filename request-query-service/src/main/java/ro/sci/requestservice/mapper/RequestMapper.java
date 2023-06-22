package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.model.Request;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PolicemanMapper.class, RequestTypeMapper.class, CommitmentMapper.class}
)
public interface RequestMapper {
    @Named("mapPolicemanToResponse")
    @Mapping(source = "policeman", target = "policemanResponse")
    @Mapping(source = "requestType", target = "requestTypeResponse")
    RequestResponse map(Request request);


    @Named("mapRequestTypeToResponse")
    @Mapping(source = "requestType", target = "requestTypeResponse")
    RequestResponse mapWithRequestType(Request request);

    @Named("mapAccountResponses")
    @Mapping(source = "policeman", target = "policemanResponse")
    @Mapping(source = "requestType", target = "requestTypeResponse")
    List<RequestResponse> map(List<Request> requests);
}
