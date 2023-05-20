package ro.sci.requestservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
