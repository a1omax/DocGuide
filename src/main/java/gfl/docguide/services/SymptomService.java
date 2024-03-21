package gfl.docguide.services;


import gfl.docguide.data.Symptom;
import gfl.docguide.repositories.SymptomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class SymptomService {
    SymptomRepository symptomRepository;


    public List<Symptom> getAllSymptoms(){
        return symptomRepository.findAll();
    }

    public Symptom saveSymptom(Symptom symptom){
        return symptomRepository.save(symptom);
    }

    public Symptom getOrSaveSymptom(String symptomName){
        Optional<Symptom> s = symptomRepository.getByNameIgnoreCase(symptomName); // todo error? duplicate maybe?
        Symptom symptom;
        symptom = s.orElseGet(() -> saveSymptom(new Symptom(symptomName)));
        return symptom;
    }

    public List<Symptom> getAllSymptomsById(List<Long> symptomIds){
        return symptomRepository.findAllById(symptomIds);
    }

}
