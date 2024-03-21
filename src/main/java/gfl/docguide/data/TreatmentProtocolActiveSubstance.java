package gfl.docguide.data;


import com.google.gson.JsonObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "treatment_protocol_active_substance")
public class TreatmentProtocolActiveSubstance {
    @EmbeddedId
    private TreatmentProtocolActiveSubstanceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @MapsId("treatmentProtocolId")
    private TreatmentProtocol treatmentProtocol;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("activeSubstanceId")
    private ActiveSubstance activeSubstance;

    @Column
    String recommendedAmount;

    public String getActiveSubstanceNameAndRecommendedAmountString(){
        return activeSubstance.getName() + ": " + recommendedAmount;
    }

    public JsonObject getAActiveSubstanceAndRecommendedAmountJSON(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("substanceName", activeSubstance.name);
        jsonObject.addProperty("recommendedAmount", recommendedAmount);
        return jsonObject;
    }
}