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
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rankName;


    @OneToMany(mappedBy = "rank", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Policeman> policemen;

    @OneToMany(mappedBy = "rank", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItSpecialist> itSpecialists;


}