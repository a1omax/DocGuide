package gfl.docguide.mappers;

import gfl.docguide.data.TreatmentProtocol;
import gfl.docguide.data.dto.TreatmentProtocolDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TreatmentProtocolActiveSubstanceMapper.class})
public interface TreatmentProtocolMapper {
    @Mapping(source = "diseaseId", target = "disease.id")
    TreatmentProtocol toEntity(TreatmentProtocolDto treatmentProtocolDto);

    @AfterMapping
    default void linkTreatmentProtocolActiveSubstances(@MappingTarget TreatmentProtocol treatmentProtocol) {
        treatmentProtocol.getTreatmentProtocolActiveSubstances().forEach(treatmentProtocolActiveSubstance -> treatmentProtocolActiveSubstance.setTreatmentProtocol(treatmentProtocol));
    }

    @Mapping(source = "disease.id", target = "diseaseId")
    TreatmentProtocolDto toDto(TreatmentProtocol treatmentProtocol);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "diseaseId", target = "disease.id")
    TreatmentProtocol partialUpdate(TreatmentProtocolDto treatmentProtocolDto, @MappingTarget TreatmentProtocol treatmentProtocol);
}