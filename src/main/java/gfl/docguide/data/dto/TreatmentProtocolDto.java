package gfl.docguide.data.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link gfl.docguide.data.TreatmentProtocol}
 */
@Value
public class TreatmentProtocolDto implements Serializable {
    Long id;
    String name;
    Long diseaseId;
    String procedures;
    List<TreatmentProtocolActiveSubstanceDto> treatmentProtocolActiveSubstances;
}