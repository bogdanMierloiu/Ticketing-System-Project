package ro.sci.requestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @OneToMany(mappedBy = "policeStructure", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Department> departments;

    @OneToMany(mappedBy = "policeStructure", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Policeman> policemen;


}