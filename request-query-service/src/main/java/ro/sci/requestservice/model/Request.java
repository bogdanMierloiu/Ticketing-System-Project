package ro.sci.requestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Policeman policeman;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private RequestType requestType;

    @Column(columnDefinition = "VARCHAR(16)")
    @Enumerated(EnumType.STRING)
    private Status status;

    private String observation;

    @Column(nullable = false)
    private String requestStructRegNo;

    private Boolean isApprovedByStructureChief;

    private String securityStructRegNo;

    private Boolean isApprovedBySecurityStructure;

    private String itStructRegNo;

    private Boolean isApprovedByITChief;

    @ManyToOne(fetch = FetchType.LAZY)
    private ItSpecialist itSpecialist;

    private LocalDateTime createdAt;
    private LocalDateTime structureChiefAppAt;
    private LocalDateTime securityStructAppAt;
    private LocalDateTime itChiefAppAt;
    private LocalDateTime solvedAt;

}
