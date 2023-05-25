package ro.sci.requestservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Column(length = 5000)
    private String observation;

    // Request Structure

    @Column(nullable = false)
    private String requestStructRegNo;

    @Column(nullable = false)
    private LocalDate regDateFromRequestStruct;

    private Boolean isApprovedByStructureChief;

    // Security Structure

    private String securityStructRegNo;

    private LocalDate regDateFromSecurityStruct;

    private Boolean isApprovedBySecurityStructure;

    // IT Structure

    private String itStructRegNo;

    private LocalDate regDateFromITStruct;

    private Boolean isApprovedByITChief;

    @ManyToOne(fetch = FetchType.LAZY)
    private ItSpecialist itSpecialist;

    private LocalDateTime createdAt;
    private LocalDateTime structureChiefAppAt;
    private LocalDateTime securityStructAppAt;
    private LocalDateTime itChiefAppAt;
    private LocalDateTime solvedAt;


}
