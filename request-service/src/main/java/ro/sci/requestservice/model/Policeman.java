package ro.sci.requestservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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


    private String firstNameSecondary;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String personalNumber;

    private String certificate;

    private LocalDate certificateValidFrom;

    private LocalDate certificateValidUntil;

    private String phoneNumber;

    @Column(nullable = false)
    private String phoneNumberPolice;

    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    private Rank rank;

    @ManyToOne(fetch = FetchType.EAGER)
    private PoliceStructure policeStructure;

    @ManyToOne(fetch = FetchType.EAGER)
    private PoliceStructureSubunit policeStructureSubunit;

    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;

    @OneToMany(mappedBy = "policeman", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Request> requests = new ArrayList<>();


}