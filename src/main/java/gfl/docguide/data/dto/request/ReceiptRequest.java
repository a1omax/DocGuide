package gfl.docguide.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ReceiptRequest {

    private String patientFirstName = "";
    private String patientLastName = "";
    private List<Long> symptomIdList = new ArrayList<>();

    @NotEmpty
    private Long diseaseId;

    private String procedures = "";
    private List<DrugIdAmount> drugIdAmountList = new ArrayList<>();

}