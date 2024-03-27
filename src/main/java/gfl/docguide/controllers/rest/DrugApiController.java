package gfl.docguide.controllers.rest;

import gfl.docguide.data.Drug;
import gfl.docguide.services.DrugService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for handling drug-related API requests.
 */
@RestController
@AllArgsConstructor
@RequestMapping(Api.V1 + "/drug")
public class DrugApiController {
    private final DrugService drugService;

    /**
     * Endpoint to find drugs based on active substance ID.
     * @param json A map containing an active substance ID.
     * @return A list of drugs associated with the provided active substance ID.
     */
    @PostMapping("/find")
    @ResponseBody
    public List<Drug> findDrug(@RequestBody Map<String, Long> json) {
        Long activeSubstanceId = json.get("activeSubstanceId");

        return drugService.getAllDrugsByActiveSubstanceId(activeSubstanceId);

    }
    /**
     * Endpoint to check if the amount of a drug is sufficient.
     * @param json A map containing a drug ID and the desired amount.
     * @return True if the amount is sufficient, false otherwise.
     */
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
