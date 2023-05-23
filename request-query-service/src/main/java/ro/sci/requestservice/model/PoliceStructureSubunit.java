package ro.sci.requestservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliceStructureSubunit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subunitName;

    @ToString.Exclude
    @OneToMany(mappedBy = "policeStructureSubunit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Department> departments;

    @ToString.Exclude
    @OneToMany(mappedBy = "policeStructureSubunit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Policeman> policemen;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private PoliceStructure policeStructure;
}