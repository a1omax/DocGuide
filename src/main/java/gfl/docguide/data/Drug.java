package gfl.docguide.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "drug")
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 1)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "active_substance_id", nullable = false)
    private ActiveSubstance activeSubstance;

    @PositiveOrZero
    @Column(name = "amount", nullable = false)
    private Integer amount;

    public Drug(String name, ActiveSubstance activeSubstance, int amount){
        this.name = name;
        this.activeSubstance = activeSubstance;
        this.amount = amount;
    }

}