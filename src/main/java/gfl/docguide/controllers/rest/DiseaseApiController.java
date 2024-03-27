package gfl.docguide.controllers.rest;


import gfl.docguide.data.Disease;
import gfl.docguide.services.DiseaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for handling disease-related API requests.
 */
@RestController
@AllArgsConstructor
@RequestMapping(Api.V1 + "/disease")
public class DiseaseApiController {
    private final DiseaseService diseaseService;

    /**
     * Endpoint to find diseases based on symptoms.
     *
     * @param listMap A map containing a list of symptom IDs.
     * @return A list of diseases matching the provided symptom IDs.
     */
    @PostMapping("/find")
    @ResponseBody
    public List<Disease> findDiseasesBySymptoms(@RequestBody Map<String, List<Long>> listMap) {

        List<Long> symptomIds = listMap.get("symptomList");

        return diseaseService.findDiseasesBySymptomIds(symptomIds);

    }
}
