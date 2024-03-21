package gfl.docguide.data;



import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false, unique = true)
    String name;

    public Symptom(String name){
        this.name = name;
    }

}
