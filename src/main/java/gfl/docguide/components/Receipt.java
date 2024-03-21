package gfl.docguide.components;

import gfl.docguide.data.Disease;
import gfl.docguide.data.Drug;
import gfl.docguide.data.Symptom;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
public class Receipt {
    String firstName;
    String lastName;
    Disease disease;
    List<Symptom> symptomList;
    String procedures;
    Map<Drug, Integer> drugAmountMap;


    public String toTextReceipt(){

        StringBuilder sb = new StringBuilder();

        sb.append("Patient: ")
                .append(firstName)
                .append(" ")
                .append(lastName);

        sb.append("\n");

        sb.append("Disease: ")
                .append(disease.getName());

        sb.append("\n");
        sb.append("Symptoms: ");
        sb.append(symptomList
                .stream()
                .map(Symptom::getName)
                .collect(Collectors.joining(", "))
        );

        sb.append("\n");

        sb.append("Procedures: ")
                .append(procedures);

        sb.append("\n");


        if (drugAmountMap!=null && !drugAmountMap.isEmpty()){
            sb.append("Drugs: ");
            for(Map.Entry<Drug, Integer> entry: drugAmountMap.entrySet()){

                sb.append("\n");
                sb.append("\tName: ")
                        .append(entry.getKey().getName());
                sb.append("\n");
                sb.append("\tAmount: ")
                        .append(entry.getValue());
            }
        }
        return sb.toString();
    }


}
