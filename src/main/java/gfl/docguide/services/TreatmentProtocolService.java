package gfl.docguide.services;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gfl.docguide.data.*;
import gfl.docguide.exceptions.DataNotFoundException;
import gfl.docguide.repositories.TreatmentProtocolActiveSubstanceRepository;
import gfl.docguide.repositories.TreatmentProtocolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Service class to handle operations related to treatment protocols.
 */
@AllArgsConstructor
@Service
public class TreatmentProtocolService {

    TreatmentProtocolRepository treatmentProtocolRepository;
    ActiveSubstanceService activeSubstanceService;
    DiseaseService diseaseService;
    private final Gson gson = new Gson();


    private final TreatmentProtocolActiveSubstanceRepository treatmentProtocolActiveSubstanceRepository;

    /**
     * Retrieves all treatment protocols.
     * @return A list of all treatment protocols.
     */
    public List<TreatmentProtocol> getAllTreatmentProtocols() {
        return treatmentProtocolRepository.findAll();
    }

    /**
     * Retrieves a treatment protocol by its ID.
     * @param id The ID of the treatment protocol to retrieve.
     * @return The TreatmentProtocol entity with the specified ID.
     * @throws DataNotFoundException If no treatment protocol is found with the provided ID.
     */
    public TreatmentProtocol getTreatmentProtocolById(Long id){
        return treatmentProtocolRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Treatment protocol was not found by id " + id));
    }

    /**
     * Saves the provided TreatmentProtocol entity.
     * @param treatmentProtocol The TreatmentProtocol entity to be saved.
     * @return The saved TreatmentProtocol entity.
     */
    public TreatmentProtocol saveTreatmentProtocol(TreatmentProtocol treatmentProtocol){
        return treatmentProtocolRepository.save(treatmentProtocol);
    }
    /**
     * Creates a new TreatmentProtocol entity from the provided parameters without active substance amounts.
     * @param params The parameters for creating the treatment protocol.
     * @return The newly created TreatmentProtocol entity.
     */
    private TreatmentProtocol createNewTreatmentProtocolFromParamsWithoutActiveSubstanceAmounts(MultiValueMap<String, String> params){


        String name = params.get("name").getFirst();
        Long diseaseId = Long.valueOf(params.get("disease").getFirst());
        String procedures = params.get("procedures").getFirst();

        Disease disease = diseaseService.getDiseaseById(diseaseId);

        return new TreatmentProtocol(name, disease, procedures);
    }

    /**
     * Adds an active substance with its recommended amount to the specified treatment protocol.
     * @param treatmentProtocol The treatment protocol to add the active substance to.
     * @param activeSubstanceName The name of the active substance.
     * @param activeSubstanceRecommendedAmount The recommended amount of the active substance.
     * @return The added TreatmentProtocolActiveSubstance entity.
     */
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

    /**
     * Deletes a treatment protocol with the specified ID.
     * @param id The ID of the treatment protocol to be deleted.
     */
    public void deleteTreatmentProtocolById(Long id) {
        treatmentProtocolRepository.deleteById(id);
    }

    /**
     * Checks if a treatment protocol exists with the specified ID.
     * @param id The ID of the treatment protocol to check.
     * @return True if a treatment protocol with the specified ID exists, false otherwise.
     */
    public boolean treatmentProtocolExistsById(Long id){
        return treatmentProtocolRepository.existsById(id);
    }

    /**
     * Updates a treatment protocol from the provided parameters.
     * @param params The parameters for updating the treatment protocol.
     * @return The updated TreatmentProtocol entity.
     * @throws DataNotFoundException If TreatmentProtocol was not found by id in params
     */
    public TreatmentProtocol updateTreatmentProtocolFromParams(MultiValueMap<String, String> params) {
        Long id = null;
        if(params.containsKey("id")){
            id = Long.valueOf(Objects.requireNonNull(params.getFirst("id")));
        }
        if (!treatmentProtocolExistsById(id)){
            throw new DataNotFoundException("Treatment protocol was not found by id");
        }

        TreatmentProtocol treatmentProtocol = createNewTreatmentProtocolFromParamsWithoutActiveSubstanceAmounts(params);
        treatmentProtocol.setId(id);

        treatmentProtocol = saveTreatmentProtocol(treatmentProtocol);

        addActiveSubstanceAmountsToTreatmentProtocol(params, treatmentProtocol);

        return treatmentProtocol;
    }

    /**
     * Saves a treatment protocol from the provided parameters.
     * @param params The parameters for creating the treatment protocol.
     * @return The saved TreatmentProtocol entity.
     */
    public TreatmentProtocol saveTreatmentProtocolFromParams(MultiValueMap<String, String> params){
        TreatmentProtocol treatmentProtocol = saveTreatmentProtocol(createNewTreatmentProtocolFromParamsWithoutActiveSubstanceAmounts(params));
        addActiveSubstanceAmountsToTreatmentProtocol(params, treatmentProtocol);
        return treatmentProtocol;
    }

    /**
     * Adds all active substance amounts to the specified treatment protocol.
     * @param params The parameters containing active substance amounts.
     * @param treatmentProtocol The treatment protocol to add active substance amounts to.
     */
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

    /**
     * Retrieves all treatment protocols associated with a specific disease ID.
     * @param id The ID of the disease.
     * @return A list of TreatmentProtocol entities associated with the specified disease ID.
     */
    public List<TreatmentProtocol> getAllTreatmentProtocolsByDiseaseId(Long id){
        return treatmentProtocolRepository.findAllByDisease_Id(id);
    }


}
