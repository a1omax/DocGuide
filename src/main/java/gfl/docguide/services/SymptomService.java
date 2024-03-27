package gfl.docguide.services;


import gfl.docguide.data.Symptom;
import gfl.docguide.exceptions.DataNotFoundException;
import gfl.docguide.repositories.SymptomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to handle operations related to symptoms.
 */
@AllArgsConstructor
@Service
public class SymptomService {

    SymptomRepository symptomRepository;

    /**
     * Retrieves all symptoms.
     *
     * @return A list of all symptoms.
     */
    public List<Symptom> getAllSymptoms() {
        return symptomRepository.findAll();
    }

    /**
     * Saves the provided Symptom entity.
     *
     * @param symptom The Symptom entity to be saved.
     * @return The saved Symptom entity.
     */
    public Symptom saveSymptom(Symptom symptom) {
        return symptomRepository.save(symptom);
    }


    /**
     * Retrieves a symptom by its name.
     *
     * @param name The name of the symptom to retrieve.
     * @return The Symptom entity with the specified name.
     * @throws DataNotFoundException If no symptom is found with the provided name.
     */
    public Symptom getSymptomByName(String name) {
        return symptomRepository.getByNameIgnoreCase(name).orElseThrow(() -> new DataNotFoundException("Symptom was not found by name " + name));
    }

    /**
     * Checks if a symptom exists with the specified name.
     *
     * @param name The name of the symptom to check.
     * @return True if a symptom with the specified name exists, false otherwise.
     */
    public boolean symptomExistByName(String name) {
        return symptomRepository.existsByNameIgnoreCase(name);
    }

    /**
     * Retrieves a symptom by its name if it exists; otherwise, saves a new symptom with the provided name.
     *
     * @param symptomName The name of the symptom to retrieve or save.
     * @return The retrieved or saved Symptom entity.
     */
    public Symptom getOrSaveSymptom(String symptomName) {
        if (symptomExistByName(symptomName)) {
            return getSymptomByName(symptomName);
        }

        return saveSymptom(new Symptom(symptomName));
    }

    /**
     * Retrieves all symptoms with the specified IDs.
     *
     * @param symptomIds The list of symptom IDs.
     * @return A list of Symptom entities with the specified IDs.
     */
    public List<Symptom> getAllSymptomsById(List<Long> symptomIds) {
        return symptomRepository.findAllById(symptomIds);
    }

}
