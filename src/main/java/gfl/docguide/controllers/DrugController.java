package gfl.docguide.controllers;

import gfl.docguide.data.Drug;
import gfl.docguide.services.DrugService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/drug")
public class DrugController {

    final DrugService drugService;


    @GetMapping("/list")
    String drugList(Model model){
        model.addAttribute("drugs", drugService.getAllDrugs());

        return "drug/drugList";
    }

    @PostMapping("/create")
    String createDrug(@RequestParam String name,
                      @RequestParam String activeSubstanceName,
                      @RequestParam int amount){

        drugService.saveDrug(name, activeSubstanceName, amount);
        return "redirect:list";
    }

    @GetMapping("/create")
    String createDrugForm(Model model){
        model.addAttribute("activeSubstances", drugService.getAllActiveSubstances());
        return "drug/createDrug";
    }

    @PostMapping("/delete")
    String deleteDrugById(@RequestParam Long id){
        drugService.deleteDrugById(id);
        return "redirect:list";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<Drug> d = drugService.getDrugById(id);
        if (d.isEmpty()){
            return "redirect:list";
        }
        Drug drug = d.get();
        model.addAttribute("drug", drug);
        model.addAttribute("activeSubstances", drugService.getAllActiveSubstances());
        return "drug/updateDrug";
    }

    @PostMapping("/update")
    public String updateDrug(@RequestParam("id") Long id, @RequestParam("name") String name,
                             @RequestParam("activeSubstanceName") String activeSubstanceName,
                             @RequestParam("amount") int amount) {
        drugService.updateDrug(id, name, activeSubstanceName, amount);
        return "redirect:list"; // Redirect to the drug list page
    }

    @PostMapping("/find")
    @ResponseBody
    public List<Drug> findDrug(@RequestBody Map<String, Long> json){
        Long activeSubstanceId = json.get("activeSubstanceId");

        return drugService.getAllDrugsByActiveSubstanceId(activeSubstanceId);

    }

    @PostMapping("/check-amount")
    @ResponseBody
    public Boolean checkDrugAmount(@RequestBody Map<String, Long> json){
        try {
            Long drugId = json.get("drugId");
            Long amount = json.get("amount");
            return drugService.checkAmount(drugId, Math.toIntExact(amount));
        } catch (ClassCastException | NullPointerException e){
            return false;
        }
    }


}
