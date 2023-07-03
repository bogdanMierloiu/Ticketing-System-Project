package ro.sci.requestservice.mapper;

import org.mapstruct.*;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.model.Request;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PolicemanMapper.class,
                RequestTypeMapper.class,
                CommitmentMapper.class,
                ItStructureRegistrationNumberMapper.class}
)
public interface RequestMapper {

    Request map(AccountRequest accountRequest);

    @Named("mapAccountResponse")
    RequestResponse map(Request request);

    List<RequestResponse> map(List<Request> requests);

    @Mapping(source = "requestType", target = "requestTypeResponse")
    @Named("mapAccountResponseWithRequestType")
    RequestResponse mapWithRequestType(Request request);


}

