package gfl.docguide.mappers;

import gfl.docguide.data.TreatmentProtocolActiveSubstance;
import gfl.docguide.data.dto.TreatmentProtocolActiveSubstanceDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TreatmentProtocolActiveSubstanceMapper {
    @Mapping(source = "activeSubstanceName", target = "activeSubstance.name")
    @Mapping(source = "idActiveSubstanceId", target = "id.activeSubstanceId")
    @Mapping(source = "idTreatmentProtocolId", target = "id.treatmentProtocolId")
    TreatmentProtocolActiveSubstance toEntity(TreatmentProtocolActiveSubstanceDto treatmentProtocolActiveSubstanceDto);

    @InheritInverseConfiguration(name = "toEntity")
    TreatmentProtocolActiveSubstanceDto toDto(TreatmentProtocolActiveSubstance treatmentProtocolActiveSubstance);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TreatmentProtocolActiveSubstance partialUpdate(TreatmentProtocolActiveSubstanceDto treatmentProtocolActiveSubstanceDto, @MappingTarget TreatmentProtocolActiveSubstance treatmentProtocolActiveSubstance);
}