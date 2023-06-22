package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.CommitmentResponse;
import ro.sci.requestservice.model.Commitment;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommitmentMapper {

    @Mapping(target = "documentData", source = "commitmentData")
    CommitmentResponse map(byte[] commitmentData);

    List<CommitmentResponse> map(List<Commitment> commitments);
}
