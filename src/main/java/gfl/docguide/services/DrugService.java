package gfl.docguide.services;

import gfl.docguide.data.ActiveSubstance;
import gfl.docguide.data.Drug;
import gfl.docguide.exceptions.DataNotFoundException;
import gfl.docguide.exceptions.DrugAmountException;
import gfl.docguide.repositories.DrugRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to handle operations related to drugs.
 */
@Service
@AllArgsConstructor
public class DrugService {
    final ActiveSubstanceService activeSubstanceService;
    final DrugRepository drugRepository;


    /**
     * Saves the provided Drug entity.
     *
     * @param drug The Drug entity to be saved.
     * @return The saved Drug entity.
     */
    public Drug saveDrug(Drug drug) {
        return drugRepository.save(drug);
    }

    /**
     * Creates and saves a new Drug entity with the provided parameters.
     * If the active substance already exists, it is fetched; otherwise, a new one is created.
     *
     * @param name                The name of the drug.
     * @param activeSubstanceName The name of the active substance in the drug.
     * @param amount              The amount of the drug.
     * @return The saved Drug entity.
     */
    public Drug saveDrug(String name, String activeSubstanceName, int amount) {
        ActiveSubstance activeSubstance;
        if (activeSubstanceService.activeSubstanceExistsByName(activeSubstanceName)) {
            activeSubstance = activeSubstanceService.getActiveSubstanceByName(activeSubstanceName);
        } else {
            activeSubstance = activeSubstanceService.saveActiveSubstance(activeSubstanceName);
        }

        return saveDrug(new Drug(name, activeSubstance, amount));
    }

    /**
     * Deletes a drug with the specified ID.
     *
     * @param id The ID of the drug to be deleted.
     */
    public void deleteDrugById(Long id) {
        drugRepository.deleteById(id);
    }

    /**
     * Retrieves all drugs.
     *
     * @return A list of all drugs.
     */
    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    /**
     * Updates an existing drug with the provided parameters.
     * If the active substance already exists, it is fetched; otherwise, a new one is created.
     * @param id The ID of the drug to be updated.
     * @param name The updated name of the drug.
     * @param activeSubstanceName The updated name of the active substance in the drug.
     * @param amount The updated amount of the drug.
     */
    public void updateDrug(Long id, String name, String activeSubstanceName, int amount) {


        Drug drug = getDrugById(id);
        drug.setName(name);
        drug.setAmount(amount);
        ActiveSubstance activeSubstance;
        if (activeSubstanceService.activeSubstanceExistsByName(activeSubstanceName)) {
            activeSubstance = activeSubstanceService.getActiveSubstanceByName(activeSubstanceName);
        } else {
            activeSubstance = activeSubstanceService.saveActiveSubstance(activeSubstanceName);
        }
        drug.setActiveSubstance(activeSubstance);
        drug.setAmount(amount);
        saveDrug(drug);
    }
    /**
     * Retrieves a drug by its ID.
     * @param id The ID of the drug to retrieve.
     * @return The Drug entity with the specified ID.
     * @throws DataNotFoundException If no drug is found with the provided ID.
     */
    public Drug getDrugById(Long id) {
        return drugRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Drug was not found by id " + id));
    }
    /**
     * Retrieves all drugs containing a specific active substance.
     * @param activeSubstanceId The ID of the active substance.
     * @return A list of drugs containing the specified active substance.
     */
    public List<Drug> getAllDrugsByActiveSubstanceId(Long activeSubstanceId) {
        return drugRepository.findByActiveSubstanceId(activeSubstanceId);
    }
    /**
     * Checks if the specified amount of a drug is available.
     * @param drugId The ID of the drug.
     * @param amount The amount to check.
     * @return True if the specified amount of the drug is available, false otherwise.
     */
    public Boolean checkAmount(Long drugId, Integer amount) {
        if (amount < 0) {
            return false;
        }

        Drug drug = getDrugById(drugId);
        return amount <= drug.getAmount();
    }
    /**
     * Prescribes a drug by reducing its amount.
     * @param drugId The ID of the drug to prescribe.
     * @param amount The amount to prescribe.
     * @throws DrugAmountException If there is not enough drug available for prescription.
     */
    public void prescribeDrug(Long drugId, Integer amount) {
        Drug drug = getDrugById(drugId);
        int amountLeft = drug.getAmount() - amount;
        if (amountLeft < 0) {
            throw new DrugAmountException("Not enough drug");
        }
        drug.setAmount(amountLeft);
        saveDrug(drug);
    }

}
