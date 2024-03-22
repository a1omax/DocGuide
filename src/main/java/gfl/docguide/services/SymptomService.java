package gfl.docguide.services;


import gfl.docguide.data.Symptom;
import gfl.docguide.exceptions.DataNotFoundException;
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

    public Symptom getSymptomByName(String name){
        return symptomRepository.getByNameIgnoreCase(name).orElseThrow(()-> new DataNotFoundException("Symptom was not found by name " + name));
    }

    public boolean symptomExistByName(String name){
        return symptomRepository.existsByNameIgnoreCase(name);
    }
    public Symptom getOrSaveSymptom(String symptomName){
        if (symptomExistByName(symptomName)){
            return getSymptomByName(symptomName);
        }

        return saveSymptom(new Symptom(symptomName));
    }

    public List<Symptom> getAllSymptomsById(List<Long> symptomIds){
        return symptomRepository.findAllById(symptomIds);
    }

}
