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
public class PoliceStructure {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String structureName;

    @OneToMany(mappedBy = "policeStructure", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PoliceStructureSubunit> policeStructureSubunits;

    @OneToMany(mappedBy = "policeStructure", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Policeman> policemen;


}