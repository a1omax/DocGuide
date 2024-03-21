package gfl.docguide.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "disease")
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "disease_symptoms",
            joinColumns = @JoinColumn(name = "disease_id"),
            inverseJoinColumns = @JoinColumn(name = "symptoms_id"))
    private List<Symptom> symptoms = new ArrayList<>();

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Disease(String name){
        this.name = name;
    }



}