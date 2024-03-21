package gfl.docguide.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TreatmentProtocolActiveSubstanceId implements Serializable {
    @Column
    private Long treatmentProtocolId;

    @Column
    private Long activeSubstanceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        TreatmentProtocolActiveSubstanceId that = (TreatmentProtocolActiveSubstanceId) o;
        return Objects.equals(treatmentProtocolId, that.treatmentProtocolId) &&
                Objects.equals(activeSubstanceId, that.activeSubstanceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(treatmentProtocolId, activeSubstanceId);
    }


}