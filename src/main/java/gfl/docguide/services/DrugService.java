package gfl.docguide.services;

import gfl.docguide.data.ActiveSubstance;
import gfl.docguide.data.Drug;
import gfl.docguide.exceptions.DataNotFoundException;
import gfl.docguide.repositories.ActiveSubstanceRepository;
import gfl.docguide.repositories.DrugRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class DrugService {
    final ActiveSubstanceService activeSubstanceService;
    final DrugRepository drugRepository;



    public void saveDrug(Drug drug) {
        drugRepository.save(drug);
    }

    public Drug saveDrug(String name, String activeSubstanceName, int amount) {
        ActiveSubstance activeSubstance;
        if (activeSubstanceService.activeSubstanceExistsByName(activeSubstanceName)){
            activeSubstance = activeSubstanceService.getActiveSubstanceByName(activeSubstanceName);
        } else {
            activeSubstance = activeSubstanceService.saveActiveSubstance(activeSubstanceName);
        }

        return drugRepository.save(new Drug(name, activeSubstance, amount));
    }


    public void deleteDrugById(Long id) {
        drugRepository.deleteById(id);
    }

    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }


    public void updateDrug(Long id, String name, String activeSubstanceName, int amount) {


        Drug drug = getDrugById(id);
        drug.setName(name);
        drug.setAmount(amount);
        ActiveSubstance activeSubstance;
        if (activeSubstanceService.activeSubstanceExistsByName(activeSubstanceName)){
            activeSubstance = activeSubstanceService.getActiveSubstanceByName(activeSubstanceName);
        }
        else {
            activeSubstance = activeSubstanceService.saveActiveSubstance(activeSubstanceName);
        }
        drug.setActiveSubstance(activeSubstance);
        drug.setAmount(amount);
        saveDrug(drug);
    }

    public Drug getDrugById(Long id) {
        return drugRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Drug was not found by id " + id));
    }

    public List<Drug> getAllDrugsByActiveSubstanceId(Long activeSubstanceId) {
        return drugRepository.findByActiveSubstanceId(activeSubstanceId);
    }

    public Boolean checkAmount(Long drugId, Integer amount) {
        if (amount < 0) {
            return false;
        }

        Drug drug = getDrugById(drugId);
        return amount <= drug.getAmount();
    }

    public void prescribeDrug(Long drugId, Integer amount) {
        Drug drug = getDrugById(drugId);
        int amountLeft = drug.getAmount() - amount;
        if (amountLeft < 0) {
            throw new RuntimeException("Not enough drug");
        }
        drug.setAmount(amountLeft);
        saveDrug(drug);
    }




}
