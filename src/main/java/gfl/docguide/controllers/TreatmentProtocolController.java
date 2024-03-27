package gfl.docguide.controllers;


import gfl.docguide.data.ActiveSubstance;
import gfl.docguide.data.Disease;
import gfl.docguide.data.TreatmentProtocol;
import gfl.docguide.mappers.TreatmentProtocolMapper;
import gfl.docguide.services.ActiveSubstanceService;
import gfl.docguide.services.DiseaseService;
import gfl.docguide.services.DrugService;
import gfl.docguide.services.TreatmentProtocolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Controller class to handle requests related to treatment protocols.
 */
@AllArgsConstructor
@Controller
@RequestMapping("/treatment-protocol")
public class TreatmentProtocolController {
    final TreatmentProtocolService treatmentProtocolService;
    final DrugService drugService;
    final DiseaseService diseaseService;
    final TreatmentProtocolMapper treatmentProtocolMapper;
    final ActiveSubstanceService activeSubstanceService;

    /**
     * Displays a list of all treatment protocols.
     * @param model The model to be populated with treatment protocol data.
     * @return The view name for displaying the treatment protocol list.
     */
    @GetMapping("/list")
    public String showTreatmentProtocols(Model model) {
        List<TreatmentProtocol> treatmentProtocols = treatmentProtocolService.getAllTreatmentProtocols();
        model.addAttribute("treatmentProtocols", treatmentProtocols);
        return "treatmentProtocol/protocolList";
    }


    /**
     * Displays the form for creating a new treatment protocol.
     * @param model The model to be populated with disease and active substance data.
     * @return The view name for displaying the create treatment protocol form.
     */
    @GetMapping("/create")
    public String showCreateTreatmentProtocolForm(Model model) {
        List<Disease> diseases = diseaseService.getAllDiseases();
        List<ActiveSubstance> activeSubstances = activeSubstanceService.getAllActiveSubstances();
        model.addAttribute("diseases", diseases);
        model.addAttribute("activeSubstances", activeSubstances);
        return "treatmentProtocol/createProtocol";
    }

    /**
     * Creates a new treatment protocol based on the submitted form data.
     * @param params The form parameters containing treatment protocol information.
     * @return Redirects to the treatment protocol list page.
     */
    @PostMapping("/create")
    public String createTreatmentProtocol(@RequestParam MultiValueMap<String, String> params) {

        treatmentProtocolService.saveTreatmentProtocolFromParams(params);

        return "redirect:list";
    }

    /**
     * Deletes a treatment protocol with the specified ID.
     * @param id The ID of the treatment protocol to be deleted.
     * @return Redirects to the treatment protocol list page.
     */
    @PostMapping("/delete")
    public String deleteTreatmentProtocol(Long id){
        treatmentProtocolService.deleteTreatmentProtocolById(id);

        return "redirect:list";
    }

    /**
     * Displays the form for updating an existing treatment protocol.
     * @param id The ID of the treatment protocol to be updated.
     * @param model The model to be populated with disease and active substance data.
     * @return The view name for displaying the update treatment protocol form.
     */
    @GetMapping("/update/{id}")
    public String showUpdateTreatmentProtocolForm(@PathVariable Long id, Model model){
        List<Disease> diseases = diseaseService.getAllDiseases();
        List<ActiveSubstance> activeSubstances = activeSubstanceService.getAllActiveSubstances();
        model.addAttribute("protocol", treatmentProtocolService.getTreatmentProtocolById(id));
        model.addAttribute("diseases", diseases);
        model.addAttribute("activeSubstances", activeSubstances);
        return "treatmentProtocol/updateProtocol";
    }
    /**
     * Updates an existing treatment protocol based on the submitted form data.
     * @param params The form parameters containing updated treatment protocol information.
     * @return Redirects to the treatment protocol list page.
     */
    @PostMapping("/update")
    public String updateTreatmentProtocol(@RequestParam MultiValueMap<String, String> params) {

        treatmentProtocolService.updateTreatmentProtocolFromParams(params);

        return "redirect:list";
    }
}
