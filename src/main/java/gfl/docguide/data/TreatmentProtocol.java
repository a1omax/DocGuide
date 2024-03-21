package gfl.docguide.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "treatment_protocol")
public class TreatmentProtocol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disease_id", nullable = false)
    private Disease disease;


    @Column(name = "procedures", length = 4096)
    private String procedures;


    @OneToMany(mappedBy = "treatmentProtocol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TreatmentProtocolActiveSubstance> treatmentProtocolActiveSubstances = new ArrayList<>();

    public TreatmentProtocol(String name, Disease disease, String procedures){
        this.name = name;
        this.disease = disease;
        this.procedures = procedures;
    }


}