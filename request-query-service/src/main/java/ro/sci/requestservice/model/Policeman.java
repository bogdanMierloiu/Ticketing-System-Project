package ro.sci.requestservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Policeman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String firstNameSecondary;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String personalNumber;

    @Column(nullable = false)
    private String certificate;

    @Column(nullable = false)
    private LocalDate certificateValidFrom;

    @Column(nullable = false)
    private LocalDate certificateValidUntil;

    private String phoneNumber;

    @Column(nullable = false)
    private String phoneNumberPolice;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rank rank;

    @ManyToOne(fetch = FetchType.LAZY)
    private PoliceStructure policeStructure;

    @ManyToOne(fetch = FetchType.LAZY)
    private PoliceStructureSubunit policeStructureSubunit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @OneToMany(mappedBy = "policeman", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Request> requests;


}