package gfl.docguide.controllers.rest;


import gfl.docguide.data.dto.TreatmentProtocolDto;
import gfl.docguide.mappers.TreatmentProtocolMapper;
import gfl.docguide.services.TreatmentProtocolService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(Api.V1 + "/treatment-protocol")
public class TreatmentProtocolApiController {
    private final TreatmentProtocolService treatmentProtocolService;
    private final TreatmentProtocolMapper treatmentProtocolMapper;
    @ResponseBody
    @PostMapping("/find")
    public List<TreatmentProtocolDto> findByDiseaseId(@RequestBody Map<String, Long> json){
        return treatmentProtocolService.getAllTreatmentProtocolsByDiseaseId(json.get("diseaseId"))
                .stream()
                .map(treatmentProtocolMapper::toDto)
                .collect(Collectors.toList());
    }

}
