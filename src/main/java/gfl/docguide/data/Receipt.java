package gfl.docguide.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@Builder
public class Receipt {
    private String firstName;
    private String lastName;
    private String diseaseName;
    private List<String> symptomNameList;
    private String procedures;
    private Map<String, Integer> drugNameAmountMap;
}
