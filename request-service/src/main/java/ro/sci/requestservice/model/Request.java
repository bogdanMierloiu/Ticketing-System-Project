package ro.sci.requestservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Policeman policeman;

    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    private String observation;

    private Boolean isApprovedByStructureChief;

    private Boolean isApprovedBySecurityStructure;

    private Boolean isApprovedByITChief;


}
