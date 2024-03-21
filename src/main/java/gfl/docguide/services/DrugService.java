package gfl.docguide.services;

import gfl.docguide.data.ActiveSubstance;
import gfl.docguide.data.Drug;
import gfl.docguide.repositories.ActiveSubstanceRepository;
import gfl.docguide.repositories.DrugRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DrugService {
    final ActiveSubstanceRepository activeSubstanceRepository;
    final DrugRepository drugRepository;

    public DrugService(ActiveSubstanceRepository activeSubstanceRepository, DrugRepository drugRepository) {
        this.activeSubstanceRepository = activeSubstanceRepository;
        this.drugRepository = drugRepository;

    }

    public void saveDrug(Drug drug) {
        drugRepository.save(drug);
    }

    public Drug saveDrug(String name, String activeSubstanceName, int amount) {
        Optional<ActiveSubstance> as = activeSubstanceRepository.findByName(activeSubstanceName);
        ActiveSubstance substance;
        substance = as.orElseGet(() -> saveActiveSubstance(activeSubstanceName));

        return drugRepository.save(new Drug(name, substance, amount));
    }

    public ActiveSubstance saveActiveSubstance(String name) {
        return activeSubstanceRepository.save(new ActiveSubstance(name));
    }


    public void deleteDrugById(Long id) {
        drugRepository.deleteById(id);
    }

    public Iterable<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }


    public void updateDrug(Long id, String name, String activeSubstanceName, int amount) {
        Optional<Drug> d = drugRepository.findById(id);
        if (d.isEmpty()) {
            return;
        }

        Drug drug = d.get();
        drug.setName(name);
        drug.setAmount(amount);

        Optional<ActiveSubstance> as = getActiveSubstanceByName(activeSubstanceName);
        ActiveSubstance activeSubstance = as.orElseGet(() -> saveActiveSubstance(activeSubstanceName));

        drug.setActiveSubstance(activeSubstance);
        drug.setAmount(amount);
        saveDrug(drug);
    }

    public List<ActiveSubstance> getAllActiveSubstances() {
        return activeSubstanceRepository.findAll();
    }


    public Optional<ActiveSubstance> getActiveSubstanceByName(String name) {
        return activeSubstanceRepository.findByName(name);
    }

    public Optional<Drug> getDrugById(Long id) {
        return drugRepository.findById(id);
    }

    public List<Drug> getAllDrugsByActiveSubstanceId(Long activeSubstanceId) {
        return drugRepository.findByActiveSubstanceId(activeSubstanceId);
    }

    public Boolean checkAmount(Long drugId, Integer amount) {
        if (amount < 0) {
            return false;
        }
        Optional<Drug> d = getDrugById(drugId);
        if (d.isEmpty()) {
            return false;
        }
        Drug drug = d.get();
        return amount <= drug.getAmount();
    }

    public void prescribeDrug(Long drugId, Integer amount) {
        Drug drug = getDrugById(drugId).orElseThrow();
        int amountLeft = drug.getAmount() - amount;
        if (amountLeft < 0) {
            throw new RuntimeException("Not enough drug");
        }
        drug.setAmount(amountLeft);
        saveDrug(drug);
    }
}
