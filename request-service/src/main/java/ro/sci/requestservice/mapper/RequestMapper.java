package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.dto.AccountResponse;
import ro.sci.requestservice.model.Request;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = PolicemanMapper.class
)
public interface RequestMapper {


    Request map(AccountRequest accountRequest);

    AccountResponse map(Request request);

    List<AccountResponse> map(List<Request> requests);
}
