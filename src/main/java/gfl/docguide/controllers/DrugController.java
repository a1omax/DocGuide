package gfl.docguide.controllers;

import gfl.docguide.data.Drug;
import gfl.docguide.services.ActiveSubstanceService;
import gfl.docguide.services.DrugService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle requests related to drugs.
 */
@AllArgsConstructor
@Controller
@RequestMapping("/drug")
public class DrugController {

    final DrugService drugService;
    final ActiveSubstanceService activeSubstanceService;

    /**
     * Displays a list of all drugs.
     *
     * @param model The model to be populated with drug data.
     * @return The view name for displaying the drug list.
     */
    @GetMapping("/list")
    String drugList(Model model) {
        model.addAttribute("drugs", drugService.getAllDrugs());

        return "drug/drugList";
    }

    /**
     * Creates a new drug based on the submitted form data.
     *
     * @param name                The name of the drug.
     * @param activeSubstanceName The name of the active substance in the drug.
     * @param amount              The amount of the drug.
     * @return Redirects to the drug list page.
     */
    @PostMapping("/create")
    String createDrug(@RequestParam String name,
                      @RequestParam String activeSubstanceName,
                      @RequestParam int amount) {

        drugService.saveDrug(name, activeSubstanceName, amount);
        return "redirect:list";
    }

    /**
     * Displays the form for creating a new drug.
     *
     * @param model The model to be populated with active substance data.
     * @return The view name for displaying the create drug form.
     */
    @GetMapping("/create")
    String createDrugForm(Model model) {
        model.addAttribute("activeSubstances", activeSubstanceService.getAllActiveSubstances());
        return "drug/createDrug";
    }

    /**
     * Deletes a drug with the specified ID.
     *
     * @param id The ID of the drug to be deleted.
     * @return Redirects to the drug list page.
     */
    @PostMapping("/delete")
    String deleteDrugById(@RequestParam Long id) {
        drugService.deleteDrugById(id);
        return "redirect:list";
    }

    /**
     * Displays the form for updating an existing drug.
     *
     * @param id    The ID of the drug to be updated.
     * @param model The model to be populated with drug and active substance data.
     * @return The view name for displaying the update drug form.
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Drug drug = drugService.getDrugById(id);

        model.addAttribute("drug", drug);
        model.addAttribute("activeSubstances", activeSubstanceService.getAllActiveSubstances());
        return "drug/updateDrug";
    }

    /**
     * Updates an existing drug based on the submitted form data.
     *
     * @param id                  The ID of the drug to be updated.
     * @param name                The updated name of the drug.
     * @param activeSubstanceName The updated name of the active substance in the drug.
     * @param amount              The updated amount of the drug.
     * @return Redirects to the drug list page.
     */
    @PostMapping("/update")
    public String updateDrug(@RequestParam("id") Long id, @RequestParam("name") String name,
                             @RequestParam("activeSubstanceName") String activeSubstanceName,
                             @RequestParam("amount") int amount) {
        drugService.updateDrug(id, name, activeSubstanceName, amount);
        return "redirect:list";
    }
}
