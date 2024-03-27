package gfl.docguide.controllers.rest;


import gfl.docguide.data.dto.TreatmentProtocolDto;
import gfl.docguide.mappers.TreatmentProtocolMapper;
import gfl.docguide.services.TreatmentProtocolService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Controller for handling treatment protocol-related API requests.
 */
@RestController
@AllArgsConstructor
@RequestMapping(Api.V1 + "/treatment-protocol")
public class TreatmentProtocolApiController {
    private final TreatmentProtocolService treatmentProtocolService;
    private final TreatmentProtocolMapper treatmentProtocolMapper;

    /**
     * Endpoint to find treatment protocols by disease ID.
     * @param json A map containing a disease ID.
     * @return A list of treatment protocol DTOs associated with the provided disease ID.
     */
    @ResponseBody
    @PostMapping("/find")
    public List<TreatmentProtocolDto> findByDiseaseId(@RequestBody Map<String, Long> json){
        return treatmentProtocolService.getAllTreatmentProtocolsByDiseaseId(json.get("diseaseId"))
                .stream()
                .map(treatmentProtocolMapper::toDto)
                .collect(Collectors.toList());
    }

}
