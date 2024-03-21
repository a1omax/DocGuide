package gfl.docguide.services;

import gfl.docguide.data.ActiveSubstance;
import gfl.docguide.repositories.ActiveSubstanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ActiveSubstanceService {
    final ActiveSubstanceRepository activeSubstanceRepository;


    public ActiveSubstance saveActiveSubstance(ActiveSubstance activeSubstance){
        return activeSubstanceRepository.save(activeSubstance);
    }
    public ActiveSubstance saveActiveSubstance(String name){
        return activeSubstanceRepository.save(new ActiveSubstance(name));
    }
    public Iterable<ActiveSubstance> getAllActiveSubstances(){
        return activeSubstanceRepository.findAll();
    }


    public ActiveSubstance getActiveSubstanceByName(String name){
        return activeSubstanceRepository.findByName(name).orElseThrow();
    }


    public void deleteActiveSubstanceById(Long id) {
        activeSubstanceRepository.deleteById(id);
    }

    public Optional<ActiveSubstance> getActiveSubstanceById(Long id) {
        return activeSubstanceRepository.findById(id);
    }

    public boolean updateActiveSubstance(Long id, String activeSubstanceName) {

        if (activeSubstanceRepository.existsByNameIgnoreCase(activeSubstanceName)){
            return false;
        }
        Optional<ActiveSubstance> as = activeSubstanceRepository.findById(id); // todo exception
        if (as.isEmpty()){
            return false;
        }
        ActiveSubstance activeSubstance = as.get();
        activeSubstance.setName(activeSubstanceName);

        activeSubstanceRepository.save(activeSubstance);
        return true;
    }
}

