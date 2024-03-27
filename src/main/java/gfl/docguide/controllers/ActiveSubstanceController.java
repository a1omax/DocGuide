package gfl.docguide.controllers;

import gfl.docguide.data.ActiveSubstance;
import gfl.docguide.services.ActiveSubstanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Controller to handle requests with active substances.
 */
@AllArgsConstructor
@Controller
@RequestMapping("/active-substance")
public class ActiveSubstanceController {

    final ActiveSubstanceService activeSubstanceService;

    /**
     * Show list page of all active substances in database.
     * @param model The model to be populated with activeSubstances data.
     * @return ActiveSubstance html response.
     */
    @GetMapping("/list")
    String showActiveSubstanceList(Model model) {
        model.addAttribute("activeSubstances", activeSubstanceService.getAllActiveSubstances());

        return "activeSubstance/substanceList";
    }

    /**
     * Create ActiveSubstance form name string in database.
     * @param name ActiveSubstance name string.
     * @return Redirect to active substance list page.
     */
    @PostMapping("/create")
    String createActiveSubstance(@RequestParam String name) {
        activeSubstanceService.saveActiveSubstance(name);
        return "redirect:list";
    }


    /**
     * Show create page for active substance.
     * @param model The model to be populated with activeSubstances data.
     * @return ActiveSubstance html response.
     */
    @GetMapping("/create")
    String showCreateActiveSubstanceForm(Model model) {
        // Fill model with all active substances
        model.addAttribute("activeSubstances", activeSubstanceService.getAllActiveSubstances());
        return "activeSubstance/createSubstance";
    }

    /**
     * Delete active substance from database by id.
     * @param id Active substance id.
     * @return Redirect to active substance list page.
     */
    @PostMapping("/delete")
    String deleteActiveSubstanceById(@RequestParam Long id) {
        activeSubstanceService.deleteActiveSubstanceById(id);
        return "redirect:list";
    }

    /**
     * Show update page for active substance.
     * @param model The model to be populated with activeSubstance data.
     * @param id ActiveSubstance id.
     * @return ActiveSubstance update view html response.
     */
    @GetMapping("/update/{id}")
    public String showUpdateActiveSubstanceForm(@PathVariable("id") Long id, Model model) {


        ActiveSubstance activeSubstance = activeSubstanceService.getActiveSubstanceById(id);
        model.addAttribute("activeSubstance", activeSubstance);
        return "activeSubstance/updateSubstance";
    }
    /**
     * Update active substance in database by id and entering new name.
     * @param id ActiveSubstance id.
     * @param name New active substance name.
     * @return Redirect to active substance list page.
     */
    @PostMapping("/update")
    public String updateActiveSubstanceName(@RequestParam Long id, String name) {
        activeSubstanceService.updateActiveSubstance(id, name);
        return "redirect:list";
    }


}
