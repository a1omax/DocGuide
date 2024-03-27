package gfl.docguide.services;

import gfl.docguide.data.Disease;
import gfl.docguide.data.Symptom;
import gfl.docguide.exceptions.DataNotFoundException;
import gfl.docguide.exceptions.ParamNotFoundException;
import gfl.docguide.repositories.DiseaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * Service class providing methods for CRUD operations on Disease entities.
 */
@AllArgsConstructor
@Service
public class DiseaseService {
    private final DiseaseRepository diseaseRepository;
    private final SymptomService symptomService;

    /**
     * Retrieves all Disease entities.
     *
     * @return A list of Disease entities.
     */
    public List<Disease> getAllDiseases() {
        return diseaseRepository.findAll();
    }

    /**
     * Retrieves a Disease entity by its ID.
     *
     * @param diseaseId The ID of the Disease entity to retrieve.
     * @return The Disease entity with the specified ID.
     * @throws DataNotFoundException If no Disease entity is found with the provided ID.
     */
    public Disease getDiseaseById(Long diseaseId) {
        return diseaseRepository.findById(diseaseId).orElseThrow(() -> new DataNotFoundException("Disease was not found by id " + diseaseId));
    }

    private Disease createDiseaseWithoutSymptoms(MultiValueMap<String, String> params) {
        String name = params.getFirst("name");
        return new Disease(name);
    }

    /**
     * Saves a Disease entity.
     *
     * @param disease The Disease entity to save.
     * @return The saved Disease entity.
     */
    public Disease saveDisease(Disease disease) {
        return diseaseRepository.save(disease);
    }

    /**
     * Creates and saves a Disease entity from the provided form parameters.
     *
     * @param params The form parameters containing disease information.
     * @return The saved Disease entity.
     */
    public Disease saveDiseaseFromParams(MultiValueMap<String, String> params) {
        Disease disease = createDiseaseWithoutSymptoms(params);
        disease = saveDisease(disease);
        addSymptomsToDiseaseFromParams(disease, params);
        saveDisease(disease);
        return disease;
    }

    private void addSymptomsToDiseaseFromParams(Disease disease, MultiValueMap<String, String> params) {
        List<String> symptomNamesList = params.get("symptom");
        if (symptomNamesList == null) {
            symptomNamesList = Collections.emptyList();
        }

        List<Symptom> symptomList = new ArrayList<>();
        for (String symptomName : symptomNamesList) {
            symptomList.add(symptomService.getOrSaveSymptom(symptomName));
        }

        disease.setSymptoms(symptomList);
    }

    /**
     * Updates a Disease entity based on the provided form parameters.
     *
     * @param params The form parameters containing updated disease information.
     */
    public void updateDiseaseFromParams(MultiValueMap<String, String> params) {
        String stringIdFromParams = params.getFirst("id");
        if (stringIdFromParams == null) {
            throw new ParamNotFoundException("Id param was not specified for disease to update");
        }

        Long id = Long.valueOf(stringIdFromParams);

        Disease disease = getDiseaseById(id);
        addSymptomsToDiseaseFromParams(disease, params);
        saveDisease(disease);
    }

    /**
     * Deletes a Disease entity by its ID.
     *
     * @param id The ID of the Disease entity to delete.
     */
    public void deleteDiseaseById(Long id) {
        diseaseRepository.deleteById(id);
    }

    /**
     * Finds Disease entities based on the provided list of symptom IDs.
     *
     * @param ids The list of symptom IDs to search for.
     * @return A list of Disease entities matching the specified symptom IDs.
     */
    public List<Disease> findDiseasesBySymptomIds(List<Long> ids) {

        return diseaseRepository.findBySymptomIds(ids);
    }
}
