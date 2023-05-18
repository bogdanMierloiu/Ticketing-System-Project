package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.RequestTypeReq;
import ro.sci.requestservice.dto.RequestTypeResponse;
import ro.sci.requestservice.model.RequestType;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RequestTypeMapper {


    RequestType map(RequestTypeReq requestTypeReq);

    RequestTypeResponse map(RequestType requestType);

    List<RequestTypeResponse> map(List<RequestType> requestTypes);
}
