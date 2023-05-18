package ro.sci.requestservice.mapper;

import org.mapstruct.*;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.dto.AccountResponse;
import ro.sci.requestservice.model.Request;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PolicemanMapper.class, RequestTypeMapper.class}
)
public interface RequestMapper {

    Request map(AccountRequest accountRequest);

    @Named("mapAccountResponse")
    AccountResponse map(Request request);

    List<AccountResponse> map(List<Request> requests);

    @Mapping(source = "requestType", target = "requestTypeResponse")
    @Named("mapAccountResponseWithRequestType")
    AccountResponse mapWithRequestType(Request request);
}

