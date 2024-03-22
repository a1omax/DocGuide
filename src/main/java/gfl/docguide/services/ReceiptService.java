package gfl.docguide.services;

import gfl.docguide.data.Receipt;
import gfl.docguide.components.receipt.ReceiptConverter;
import gfl.docguide.data.Disease;
import gfl.docguide.data.ReceiptDocument;
import gfl.docguide.data.Symptom;
import gfl.docguide.data.dto.request.DrugIdAmount;
import gfl.docguide.data.dto.request.ReceiptRequest;
import gfl.docguide.repositories.ReceiptDocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ReceiptService {
    DiseaseService diseaseService;
    SymptomService symptomService;
    DrugService drugService;

    ReceiptConverter receiptConverter;
    ReceiptDocumentRepository receiptDocumentRepository;

    public ReceiptDocument saveReceiptDocument(Receipt receipt){
        String docText = receiptConverter.convert(receipt);
        return receiptDocumentRepository.save(new ReceiptDocument(docText));
    }

    public Receipt createReceipt(ReceiptRequest request) {
        Disease disease = diseaseService.getDiseaseById(request.getDiseaseId());

        List<DrugIdAmount> drugIdAmountList = request.getDrugIdAmountList();

        Map<String, Integer> drugAmountMap = new HashMap<>();
        for (DrugIdAmount drugIdAmount : drugIdAmountList) {
            Long drugId = drugIdAmount.getDrugId();
            Integer amount = drugIdAmount.getAmount();

            if (!drugService.checkAmount(drugId, amount)) {
                throw new RuntimeException("Not enough amount for specified drug id");
            }
            drugAmountMap.put(drugService.getDrugById(drugId).getName(), amount);
        }

        // If no exceptions:
        drugIdAmountList.forEach(d -> drugService.prescribeDrug(d.getDrugId(), d.getAmount()));


        // If some symptoms was not found is not critical error
        List<String> symptomNameList = symptomService.getAllSymptomsById(request.getSymptomIdList())
                .stream().map(Symptom::getName).toList();



        return Receipt.builder()
                .firstName(request.getPatientFirstName())
                .lastName(request.getPatientLastName())
                .procedures(request.getProcedures())
                .diseaseName(disease.getName())
                .drugNameAmountMap(drugAmountMap)
                .symptomNameList(symptomNameList)
                .build();
    }
}
