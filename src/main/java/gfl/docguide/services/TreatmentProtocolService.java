package gfl.docguide.services;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gfl.docguide.data.*;
import gfl.docguide.repositories.TreatmentProtocolActiveSubstanceRepository;
import gfl.docguide.repositories.TreatmentProtocolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;
import java.util.*;


@AllArgsConstructor
@Service
public class TreatmentProtocolService {

    TreatmentProtocolRepository treatmentProtocolRepository;
    ActiveSubstanceService activeSubstanceService;
    DiseaseService diseaseService;
    private final Gson gson = new Gson();


    private final TreatmentProtocolActiveSubstanceRepository treatmentProtocolActiveSubstanceRepository;


    public List<TreatmentProtocol> getAllTreatmentProtocols() {
        return treatmentProtocolRepository.findAll();
    }

    public TreatmentProtocol getTreatmentProtocolById(Long id){
        return treatmentProtocolRepository.findById(id).orElseThrow();
    }


    public TreatmentProtocol saveTreatmentProtocol(TreatmentProtocol treatmentProtocol){
        return treatmentProtocolRepository.save(treatmentProtocol);
    }

    private TreatmentProtocol createNewTreatmentProtocolFromParamsWithoutActiveSubstanceAmounts(MultiValueMap<String, String> params){


        String name = params.get("name").getFirst();
        Long diseaseId = Long.valueOf(params.get("disease").getFirst());
        String procedures = params.get("procedures").getFirst();

        Disease disease = diseaseService.getDiseaseById(diseaseId).orElseThrow();

        return new TreatmentProtocol(name, disease, procedures);
    }


    public TreatmentProtocolActiveSubstance addActiveSubstanceAmount(TreatmentProtocol treatmentProtocol,
                                         String activeSubstanceName,
                                         String activeSubstanceRecommendedAmount){
        ActiveSubstance activeSubstance = activeSubstanceService.getActiveSubstanceByName(activeSubstanceName);


        TreatmentProtocolActiveSubstanceId tpasId =
                new TreatmentProtocolActiveSubstanceId(treatmentProtocol.getId(), activeSubstance.getId());

        TreatmentProtocolActiveSubstance tpas =
                new TreatmentProtocolActiveSubstance(tpasId, treatmentProtocol, activeSubstance, activeSubstanceRecommendedAmount);

        return treatmentProtocolActiveSubstanceRepository.save(tpas);
    }

    public void deleteTreatmentProtocolById(Long id) {
        treatmentProtocolRepository.deleteById(id);
    }

    public boolean treatmentProtocolExistsById(Long id){
        return treatmentProtocolRepository.existsById(id);
    }

    public TreatmentProtocol updateTreatmentProtocolFromParams(MultiValueMap<String, String> params) {
        Long id = null;
        if(params.containsKey("id")){
            // todo exception
            id = Long.valueOf(Objects.requireNonNull(params.getFirst("id")));
        }
        if (!treatmentProtocolExistsById(id)){
            return null;
        }

        TreatmentProtocol treatmentProtocol = createNewTreatmentProtocolFromParamsWithoutActiveSubstanceAmounts(params);
        treatmentProtocol.setId(id);

        treatmentProtocol = saveTreatmentProtocol(treatmentProtocol);

        addActiveSubstanceAmountsToTreatmentProtocol(params, treatmentProtocol);

        return treatmentProtocol;
    }


    public TreatmentProtocol saveTreatmentProtocolFromParams(MultiValueMap<String, String> params){
        TreatmentProtocol treatmentProtocol = saveTreatmentProtocol(createNewTreatmentProtocolFromParamsWithoutActiveSubstanceAmounts(params));
        addActiveSubstanceAmountsToTreatmentProtocol(params, treatmentProtocol);
        return treatmentProtocol;
    }


    public void addActiveSubstanceAmountsToTreatmentProtocol(MultiValueMap<String, String> params,
                                                              TreatmentProtocol treatmentProtocol){

        List<String> activeSubstanceAmountsJsonStringList = params.get("activeSubstanceAmount");
        if (activeSubstanceAmountsJsonStringList == null){
            return;
        }


        Type type = new TypeToken<Map<String, String>>(){}.getType();
        for(String jsonASA: activeSubstanceAmountsJsonStringList){

            Map<String, String> mapASA = gson.fromJson(jsonASA, type);
            addActiveSubstanceAmount(treatmentProtocol,
                    mapASA.get("substanceName"),
                    mapASA.get("recommendedAmount"));
        }
    }


    public List<TreatmentProtocol> getAllTreatmentProtocolsByDiseaseId(Long id){
        return treatmentProtocolRepository.findAllByDisease_Id(id);
    }

/*
    public TreatmentProtocolDto convertToDTO(TreatmentProtocol treatmentProtocol){
        TreatmentProtocolDto treatmentProtocolDto = new TreatmentProtocolDto();

        treatmentProtocolDto.setId(treatmentProtocol.getId());
        treatmentProtocolDto.setName(treatmentProtocol.getName());
        treatmentProtocolDto.setProcedures(treatmentProtocol.getProcedures());
        treatmentProtocolDto.setTreatmentProtocolActiveSubstances(
                treatmentProtocol.getTreatmentProtocolActiveSubstances().stream().map()
        );

        return null;
    }
*/



}
