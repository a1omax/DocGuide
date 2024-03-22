package gfl.docguide.controllers.rest;

import gfl.docguide.data.Drug;
import gfl.docguide.services.DrugService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(Api.V1 + "/drug")
public class DrugApiController {
    private final DrugService drugService;

    @PostMapping("/find")
    @ResponseBody
    public List<Drug> findDrug(@RequestBody Map<String, Long> json) {
        Long activeSubstanceId = json.get("activeSubstanceId");

        return drugService.getAllDrugsByActiveSubstanceId(activeSubstanceId);

    }

    @PostMapping("/check-amount")
    @ResponseBody
    public Boolean checkDrugAmount(@RequestBody Map<String, Long> json) {
        try {
            Long drugId = json.get("drugId");
            Long amount = json.get("amount");
            return drugService.checkAmount(drugId, Math.toIntExact(amount));
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }
}
