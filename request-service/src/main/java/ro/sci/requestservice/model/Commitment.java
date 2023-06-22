package ro.sci.requestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Commitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] documentData;

    @OneToOne
    @JsonIgnore
    private Request request;

    @Override
    public String toString() {
        return "Commitment{" +
                "id=" + id +
                ", documentData=" + Arrays.toString(documentData) +
                '}';
    }
}
