package gfl.docguide.services;

import gfl.docguide.data.ActiveSubstance;
import gfl.docguide.exceptions.DataNotFoundException;
import gfl.docguide.repositories.ActiveSubstanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class providing methods for CRUD operations on ActiveSubstance entities.
 */
@AllArgsConstructor
@Service
public class ActiveSubstanceService {
    final ActiveSubstanceRepository activeSubstanceRepository;

    /**
     * Saves the provided ActiveSubstance entity.
     * @param activeSubstance ActiveSubstance entity to be saved.
     * @return Saved ActiveSubstance entity.
     */
    public ActiveSubstance saveActiveSubstance(ActiveSubstance activeSubstance){
        return activeSubstanceRepository.save(activeSubstance);
    }
    /**
     * Creates and saves a new ActiveSubstance entity with the provided name.
     * @param name The name of the ActiveSubstance entity to be created.
     * @return Saved ActiveSubstance entity.
     */
    public ActiveSubstance saveActiveSubstance(String name){
        return saveActiveSubstance(new ActiveSubstance(name));
    }

    /**
     * Retrieves all ActiveSubstance entities.
     * @return A list of ActiveSubstance entities.
     */
    public List<ActiveSubstance> getAllActiveSubstances(){
        return activeSubstanceRepository.findAll();
    }

    /**
     * Retrieves an ActiveSubstance entity by its name.
     * @param name The name of the ActiveSubstance entity to retrieve.
     * @return The ActiveSubstance entity with the specified name.
     * @throws DataNotFoundException If no ActiveSubstance entity is found with the provided name.
     */
    public ActiveSubstance getActiveSubstanceByName(String name){
        return activeSubstanceRepository.findByName(name).orElseThrow(() -> new DataNotFoundException("Active substance was not found by name " + name));
    }
    /**
     * Retrieves an ActiveSubstance entity by its ID.
     * @param id The ID of the ActiveSubstance entity to retrieve.
     * @return The ActiveSubstance entity with the specified ID.
     * @throws DataNotFoundException If no ActiveSubstance entity is found with the provided ID.
     */
    public ActiveSubstance getActiveSubstanceById(Long id) {
        return activeSubstanceRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Active substance was not found by id " + id));
    }
    /**
     * Deletes an ActiveSubstance entity by its ID.
     * @param id The ID of the ActiveSubstance entity to delete.
     */
    public void deleteActiveSubstanceById(Long id) {
        activeSubstanceRepository.deleteById(id);
    }

    /**
     * Updates the name of an ActiveSubstance entity.
     * @param id The ID of the ActiveSubstance entity to update.
     * @param activeSubstanceName The new name for the ActiveSubstance entity.
     * @throws DataIntegrityViolationException If another entity exists under specified activeSubstanceName.
     */
    public void updateActiveSubstance(Long id, String activeSubstanceName) {

        if (activeSubstanceRepository.existsByNameIgnoreCaseAndIdIsNot(activeSubstanceName, id)){
            throw new DataIntegrityViolationException("Another active substance with same name already exists");
        }
        ActiveSubstance activeSubstance = getActiveSubstanceById(id);
        activeSubstance.setName(activeSubstanceName);
        saveActiveSubstance(activeSubstance);
    }
    /**
     * Checks if an ActiveSubstance entity exists with the specified name.
     * @param name The name of the ActiveSubstance entity to check.
     * @return True if an ActiveSubstance entity with the specified name exists, false otherwise.
     */
    public boolean activeSubstanceExistsByName(String name){
        return activeSubstanceRepository.existsByNameIgnoreCase(name);
    }


}

