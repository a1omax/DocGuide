package gfl.docguide.services;

import gfl.docguide.components.Receipt;
import gfl.docguide.data.Disease;
import gfl.docguide.data.Drug;
import gfl.docguide.data.Symptom;
import gfl.docguide.data.dto.request.DrugIdAmount;
import gfl.docguide.data.dto.request.ReceiptRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ReceiptService {
    DiseaseService diseaseService;
    SymptomService symptomService;
    DrugService drugService;

    public Receipt createReceipt(ReceiptRequest request) {
        Disease disease = diseaseService.getDiseaseById(request.getDiseaseId()).orElseThrow();

        List<DrugIdAmount> drugIdAmountList = request.getDrugIdAmountList();

        Map<Drug, Integer> drugAmountMap = new HashMap<>();
        for (DrugIdAmount drugIdAmount : drugIdAmountList) {
            Long drugId = drugIdAmount.getDrugId();
            Integer amount = drugIdAmount.getAmount();

            if (!drugService.checkAmount(drugId, amount)) {
                throw new RuntimeException("Not enough amount for specified drug id");
            }
            drugAmountMap.put(drugService.getDrugById(drugId).orElseThrow(), amount);
        }

        // If no exceptions:
        drugIdAmountList.forEach(d -> drugService.prescribeDrug(d.getDrugId(), d.getAmount()));


        // If some symptoms was not found is not critical error
        List<Symptom> symptomList = symptomService.getAllSymptomsById(request.getSymptomIdList());


        return Receipt.builder()
                .firstName(request.getPatientFirstName())
                .lastName(request.getPatientLastName())
                .procedures(request.getProcedures())
                .disease(disease)
                .drugAmountMap(drugAmountMap)
                .symptomList(symptomList)
                .build();
    }



}
