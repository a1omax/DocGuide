package gfl.docguide.data.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link gfl.docguide.data.TreatmentProtocolActiveSubstance}
 */
@Value
public class TreatmentProtocolActiveSubstanceDto implements Serializable {

    Long idActiveSubstanceId;
    Long idTreatmentProtocolId;
    String activeSubstanceName;
    String recommendedAmount;
}