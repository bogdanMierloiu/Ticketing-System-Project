package ro.sci.ticketweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ItSpecialistResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private RankResponse rank;

}
