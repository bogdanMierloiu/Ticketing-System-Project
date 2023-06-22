package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.CommitmentResponse;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommitmentMapper {

    @Mapping(target = "documentData", source = "commitmentData")
    CommitmentResponse map(byte[] commitmentData);


    @Mapping(target = "documentData", source = "commitmentData")
    List<CommitmentResponse> map(List<byte[]> commitmentsData);
}
