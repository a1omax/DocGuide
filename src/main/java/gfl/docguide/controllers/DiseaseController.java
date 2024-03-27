package gfl.docguide.controllers;

import gfl.docguide.services.DiseaseService;
import gfl.docguide.services.SymptomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle requests related to diseases.
 */
@AllArgsConstructor
@Controller
@RequestMapping("/disease")
public class DiseaseController {

    DiseaseService diseaseService;
    SymptomService symptomService;


    /**
     * Displays a list of all diseases.
     * @param model The model to be populated with disease data.
     * @return The view name for displaying the disease list.
     */
    @GetMapping("/list")
    String showAllDiseases(Model model){
        model.addAttribute("diseaseList", diseaseService.getAllDiseases());
        return "disease/diseaseList";
    }

    /**
     * Displays the form for creating a new disease.
     * @param model The model to be populated with symptom data.
     * @return The view name for displaying the create disease form.
     */
    @GetMapping("/create")
    String showCreateDiseaseForm(Model model){
        model.addAttribute("symptomList", symptomService.getAllSymptoms());
        return "disease/createDisease";
    }
    /**
     * Creates a new disease based on the submitted form data.
     * @param params The form parameters containing disease information.
     * @return Redirects to the disease list page.
     */
    @PostMapping("/create")
    String createDisease(@RequestParam MultiValueMap<String, String> params){
        diseaseService.saveDiseaseFromParams(params);
        return "redirect:list";
    }
    /**
     * Displays the form for updating an existing disease.
     * @param id The ID of the disease to be updated.
     * @param model The model to be populated with symptom and disease data.
     * @return The view name for displaying the update disease form.
     */
    @GetMapping("/update/{id}")
    String showDiseaseUpdateFrom(@PathVariable Long id, Model model){
        model.addAttribute("symptomList", symptomService.getAllSymptoms());
        model.addAttribute("disease", diseaseService.getDiseaseById(id));
        return "disease/updateDisease";
    }
    /**
     * Updates an existing disease based on the submitted form data.
     * @param params The form parameters containing updated disease information.
     * @return Redirects to the disease list page.
     */
    @PostMapping("/update")
    String updateDisease(@RequestParam MultiValueMap<String, String> params){
        diseaseService.updateDiseaseFromParams(params);
        return "redirect:list";
    }
    /**
     * Deletes a disease with the specified ID.
     * @param id The ID of the disease to be deleted.
     * @return Redirects to the disease list page.
     */
    @PostMapping("/delete")
    String deleteDisease(@RequestParam Long id){
        diseaseService.deleteDiseaseById(id);
        return "redirect:list";
    }
}
