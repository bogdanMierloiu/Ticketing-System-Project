package ro.sci.requestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Policeman policeman;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "it_registration_number_id")
    private ItStructureRegistrationNumber itStructRegNo;

    private LocalDate regDateFromITStruct;

    private Boolean isApprovedByITChief;

    @ManyToOne(fetch = FetchType.EAGER)
    private ItSpecialist itSpecialist;

    private LocalDateTime createdAt;
    private LocalDateTime structureChiefAppAt;
    private LocalDateTime securityStructAppAt;
    private LocalDateTime itChiefAppAt;
    private LocalDateTime solvedAt;


    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Commitment commitment;


}
