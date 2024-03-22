package gfl.docguide.services;

import gfl.docguide.data.ActiveSubstance;
import gfl.docguide.exceptions.DataNotFoundException;
import gfl.docguide.repositories.ActiveSubstanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class ActiveSubstanceService {
    final ActiveSubstanceRepository activeSubstanceRepository;


    public ActiveSubstance saveActiveSubstance(ActiveSubstance activeSubstance){
        return activeSubstanceRepository.save(activeSubstance);
    }
    public ActiveSubstance saveActiveSubstance(String name){
        return saveActiveSubstance(new ActiveSubstance(name));
    }
    public List<ActiveSubstance> getAllActiveSubstances(){
        return activeSubstanceRepository.findAll();
    }


    public ActiveSubstance getActiveSubstanceByName(String name){
        return activeSubstanceRepository.findByName(name).orElseThrow(() -> new DataNotFoundException("Active substance was not found by name " + name));
    }

    public ActiveSubstance getActiveSubstanceById(Long id) {
        return activeSubstanceRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Active substance was not found by id " + id));
    }
    public void deleteActiveSubstanceById(Long id) {
        activeSubstanceRepository.deleteById(id);
    }


    public boolean updateActiveSubstance(Long id, String activeSubstanceName) {

        if (activeSubstanceRepository.existsByNameIgnoreCaseAndIdIsNot(activeSubstanceName, id)){
            return false;
        }
        ActiveSubstance activeSubstance = getActiveSubstanceById(id);
        activeSubstance.setName(activeSubstanceName);
        saveActiveSubstance(activeSubstance);
        return true;
    }

    public boolean activeSubstanceExistsByName(String name){
        return activeSubstanceRepository.existsByNameIgnoreCase(name);
    }


}

