package gfl.docguide.controllers;

import gfl.docguide.data.ActiveSubstance;
import gfl.docguide.services.ActiveSubstanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/active-substance")
public class ActiveSubstanceController {

    final ActiveSubstanceService activeSubstanceService;

    public ActiveSubstanceController(ActiveSubstanceService activeSubstanceService) {
        this.activeSubstanceService = activeSubstanceService;
    }


    @GetMapping("/list")
    String showActiveSubstanceList(Model model) {
        model.addAttribute("activeSubstances", activeSubstanceService.getAllActiveSubstances());

        return "activeSubstance/substanceList";
    }

    @PostMapping("/create")
    String createActiveSubstance(@RequestParam String name) {
        activeSubstanceService.saveActiveSubstance(name);
        return "redirect:list";
    }

    @GetMapping("/create")
    String showCreateActiveSubstanceForm(Model model) {
        // to check if exists
        model.addAttribute("activeSubstances", activeSubstanceService.getAllActiveSubstances());
        return "activeSubstance/createSubstance";
    }

    @PostMapping("/delete")
    String deleteActiveSubstanceById(@RequestParam Long id) {
        activeSubstanceService.deleteActiveSubstanceById(id);
        return "redirect:list";
    }


    @GetMapping("/update/{id}")
    public String showUpdateActiveSubstanceForm(@PathVariable("id") Long id, Model model) {


        ActiveSubstance activeSubstance = activeSubstanceService.getActiveSubstanceById(id);
        model.addAttribute("activeSubstance", activeSubstance);
        model.addAttribute("activeSubstanceList", activeSubstanceService.getAllActiveSubstances());
        return "activeSubstance/updateSubstance";
    }

    @PostMapping("/update")
    public String updateActiveSubstanceName(@RequestParam Long id, String name) {
        if (!activeSubstanceService.updateActiveSubstance(id, name)) {
            return "redirect:update";
        }
        return "redirect:list"; // Redirect to the drug list page
    }


}
