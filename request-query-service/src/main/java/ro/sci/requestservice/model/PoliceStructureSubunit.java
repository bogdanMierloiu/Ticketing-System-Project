package ro.sci.requestservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "policeStructureSubunit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Department> departments;

    @OneToMany(mappedBy = "policeStructureSubunit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Policeman> policemen;

    @ManyToOne(fetch = FetchType.LAZY)
    private PoliceStructure policeStructure;
}