package gfl.docguide.services;

import gfl.docguide.data.Disease;
import gfl.docguide.data.Symptom;
import gfl.docguide.exceptions.DataNotFoundException;
import gfl.docguide.repositories.DiseaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.*;

@AllArgsConstructor
@Service
public class DiseaseService {
    private final DiseaseRepository diseaseRepository;
    private final SymptomService symptomService;

    public List<Disease> getAllDiseases() {
        return diseaseRepository.findAll();
    }

    public Disease getDiseaseById(Long diseaseId) {
        return diseaseRepository.findById(diseaseId).orElseThrow(()-> new DataNotFoundException("Disease was not found by id " + diseaseId));
    }

    private Disease createDiseaseWithoutSymptoms(MultiValueMap<String, String> params){
        String name = params.getFirst("name");
        return new Disease(name);
    }


    public Disease saveDisease(Disease disease){
        return diseaseRepository.save(disease);
    }

    public Disease saveDiseaseFromParams(MultiValueMap<String, String> params){
        Disease disease = createDiseaseWithoutSymptoms(params);
        disease = saveDisease(disease);
        addSymptomsToDiseaseFromParams(disease, params);
        saveDisease(disease);
        return disease;
    }

    private void addSymptomsToDiseaseFromParams(Disease disease, MultiValueMap<String, String> params) {
        List<String> symptomNamesList = params.get("symptom");
        if (symptomNamesList == null){
            symptomNamesList = Collections.emptyList();
        }

        List<Symptom> symptomList = new ArrayList<>();
        for (String symptomName: symptomNamesList){
            symptomList.add(symptomService.getOrSaveSymptom(symptomName));
        }

        disease.setSymptoms(symptomList);
    }

    public boolean updateDiseaseFromParams(MultiValueMap<String, String> params) {
        String stringIdFromParams = params.getFirst("id");
        if (stringIdFromParams == null){
            return false;
        }

        Long id = Long.valueOf(stringIdFromParams);

        Disease disease = getDiseaseById(id);
        addSymptomsToDiseaseFromParams(disease, params);
        saveDisease(disease);

        return true;
    }

    public void deleteDiseaseById(Long id) {
        diseaseRepository.deleteById(id);
    }

    public List<Disease> findDiseasesBySymptomIds(List<Long> ids) {

        return diseaseRepository.findBySymptomIds(ids);
    }
}
