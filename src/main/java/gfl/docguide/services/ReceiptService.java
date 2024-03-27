package gfl.docguide.services;

import gfl.docguide.data.Receipt;
import gfl.docguide.components.receipt.ReceiptConverter;
import gfl.docguide.data.Disease;
import gfl.docguide.data.ReceiptDocument;
import gfl.docguide.data.Symptom;
import gfl.docguide.data.dto.request.DrugIdAmount;
import gfl.docguide.data.dto.request.ReceiptRequest;
import gfl.docguide.exceptions.DrugAmountException;
import gfl.docguide.repositories.ReceiptDocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class to handle operations related to receipts.
 */
@Service
@AllArgsConstructor
public class ReceiptService {
    DiseaseService diseaseService;
    SymptomService symptomService;
    DrugService drugService;

    ReceiptConverter receiptConverter;
    ReceiptDocumentRepository receiptDocumentRepository;

    /**
     * Saves the generated receipt document.
     * @param receipt The receipt to be converted and saved.
     * @return The saved receipt document.
     */
    public ReceiptDocument saveReceiptDocument(Receipt receipt) {
        String docText = receiptConverter.convert(receipt);
        return receiptDocumentRepository.save(new ReceiptDocument(docText));
    }
    /**
     * Creates a receipt based on the provided request data.
     * @param request The receipt request containing patient and treatment information.
     * @return The generated receipt.
     * @throws DrugAmountException If there is not enough amount for a specified drug.
     */
    public Receipt createReceipt(ReceiptRequest request) {
        Disease disease = diseaseService.getDiseaseById(request.getDiseaseId());

        List<DrugIdAmount> drugIdAmountList = request.getDrugIdAmountList();

        Map<String, Integer> drugAmountMap = new HashMap<>();
        for (DrugIdAmount drugIdAmount : drugIdAmountList) {
            Long drugId = drugIdAmount.getDrugId();
            Integer amount = drugIdAmount.getAmount();

            if (!drugService.checkAmount(drugId, amount)) {
                throw new DrugAmountException("Not enough amount for specified drug id");
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
