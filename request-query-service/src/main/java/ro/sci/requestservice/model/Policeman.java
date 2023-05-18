package ro.sci.requestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode
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
    private String lastName;

    @Column(nullable = false)
    private String personalNumber;

    @Column(nullable = false)
    private String certificate;

    @Column(nullable = false)
    private String phoneNumber;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Rank rank;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private PoliceStructure policeStructure;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Department department;

    @OneToMany(mappedBy = "policeman", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Request> requests;


}