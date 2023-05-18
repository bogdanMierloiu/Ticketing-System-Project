package ro.sci.requestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ro.sci.requestservice.dto.RankRequest;
import ro.sci.requestservice.dto.RankResponse;
import ro.sci.requestservice.model.Rank;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RankMapper {


    Rank map(RankRequest rankRequest);

    RankResponse map(Rank rank);

    List<RankResponse> map(List<Rank> ranks);
}
