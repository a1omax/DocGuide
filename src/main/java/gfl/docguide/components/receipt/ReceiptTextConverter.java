package gfl.docguide.components.receipt;


import gfl.docguide.data.Receipt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@AllArgsConstructor
public class ReceiptTextConverter implements ReceiptConverter{


    @Override
    public String convert(Receipt receipt) {
        StringBuilder sb = new StringBuilder();

        sb.append("Patient: ")
                .append(receipt.getFirstName())
                .append(" ")
                .append(receipt.getLastName());

        sb.append("\n");

        sb.append("Disease: ")
                .append(receipt.getDiseaseName());

        sb.append("\n");
        sb.append("Symptoms: ");
        sb.append(String.join(", ", receipt.getSymptomNameList())
        );

        sb.append("\n");

        sb.append("Procedures: ")
                .append(receipt.getProcedures());

        sb.append("\n");


        if (receipt.getDrugNameAmountMap()!=null && !receipt.getDrugNameAmountMap().isEmpty()){
            sb.append("Drugs: ");
            for(Map.Entry<String, Integer> entry: receipt.getDrugNameAmountMap().entrySet()){

                sb.append("\n");
                sb.append("\tName: ")
                        .append(entry.getKey());
                sb.append("\n");
                sb.append("\tAmount: ")
                        .append(entry.getValue());
            }
        }
        return sb.toString();
    }
}
