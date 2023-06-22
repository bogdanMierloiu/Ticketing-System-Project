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

    private String documentName;

    @Lob
    private byte[] documentData;

    private Boolean isFromAdmin;

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
