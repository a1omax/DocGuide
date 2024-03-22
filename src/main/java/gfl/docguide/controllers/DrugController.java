package gfl.docguide.controllers;

import gfl.docguide.data.Drug;
import gfl.docguide.services.ActiveSubstanceService;
import gfl.docguide.services.DrugService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@Controller
@RequestMapping("/drug")
public class DrugController {

    final DrugService drugService;
    final ActiveSubstanceService activeSubstanceService;

    @GetMapping("/list")
    String drugList(Model model) {
        model.addAttribute("drugs", drugService.getAllDrugs());

        return "drug/drugList";
    }

    @PostMapping("/create")
    String createDrug(@RequestParam String name,
                      @RequestParam String activeSubstanceName,
                      @RequestParam int amount) {

        drugService.saveDrug(name, activeSubstanceName, amount);
        return "redirect:list";
    }

    @GetMapping("/create")
    String createDrugForm(Model model) {
        model.addAttribute("activeSubstances", activeSubstanceService.getAllActiveSubstances());
        return "drug/createDrug";
    }

    @PostMapping("/delete")
    String deleteDrugById(@RequestParam Long id) {
        drugService.deleteDrugById(id);
        return "redirect:list";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Drug drug = drugService.getDrugById(id);

        model.addAttribute("drug", drug);
        model.addAttribute("activeSubstances", activeSubstanceService.getAllActiveSubstances());
        return "drug/updateDrug";
    }

    @PostMapping("/update")
    public String updateDrug(@RequestParam("id") Long id, @RequestParam("name") String name,
                             @RequestParam("activeSubstanceName") String activeSubstanceName,
                             @RequestParam("amount") int amount) {
        drugService.updateDrug(id, name, activeSubstanceName, amount);
        return "redirect:list"; // Redirect to the drug list page
    }




}
