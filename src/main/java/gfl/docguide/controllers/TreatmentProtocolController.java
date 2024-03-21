package gfl.docguide.controllers;


import gfl.docguide.data.ActiveSubstance;
import gfl.docguide.data.Disease;
import gfl.docguide.data.TreatmentProtocol;
import gfl.docguide.data.dto.TreatmentProtocolDto;
import gfl.docguide.mappers.TreatmentProtocolMapper;
import gfl.docguide.services.DiseaseService;
import gfl.docguide.services.DrugService;
import gfl.docguide.services.TreatmentProtocolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@AllArgsConstructor
@Controller
@RequestMapping("/treatment-protocol")
public class TreatmentProtocolController {
    private final TreatmentProtocolService treatmentProtocolService;
    private final DrugService drugService;
    private final DiseaseService diseaseService;
    private final TreatmentProtocolMapper treatmentProtocolMapper;


    @GetMapping("/list")
    public String showTreatmentProtocols(Model model) {
        List<TreatmentProtocol> treatmentProtocols = treatmentProtocolService.getAllTreatmentProtocols();
        model.addAttribute("treatmentProtocols", treatmentProtocols);
        return "treatmentProtocol/protocolList";
    }

    @GetMapping("/create")
    public String showCreateTreatmentProtocolForm(Model model) {
        List<Disease> diseases = diseaseService.getAllDiseases();
        List<ActiveSubstance> activeSubstances = drugService.getAllActiveSubstances();
        model.addAttribute("diseases", diseases);
        model.addAttribute("activeSubstances", activeSubstances);
        return "treatmentProtocol/createProtocol";
    }

    @PostMapping("/create")
    public String createTreatmentProtocol(@RequestParam MultiValueMap<String, String> params) {

        treatmentProtocolService.saveTreatmentProtocolFromParams(params);

        return "redirect:list";
    }


    @PostMapping("/delete")
    public String deleteTreatmentProtocol(Long id){
        treatmentProtocolService.deleteTreatmentProtocolById(id);

        return "redirect:list";
    }


    @GetMapping("/update/{id}")
    public String showUpdateTreatmentProtocolForm(@PathVariable Long id, Model model){
        List<Disease> diseases = diseaseService.getAllDiseases();
        List<ActiveSubstance> activeSubstances = drugService.getAllActiveSubstances();
        model.addAttribute("protocol", treatmentProtocolService.getTreatmentProtocolById(id));
        model.addAttribute("diseases", diseases);
        model.addAttribute("activeSubstances", activeSubstances);
        return "treatmentProtocol/updateProtocol";
    }

    @PostMapping("/update")
    public String updateTreatmentProtocol(@RequestParam MultiValueMap<String, String> params) {

        treatmentProtocolService.updateTreatmentProtocolFromParams(params);

        return "redirect:list";
    }

    @ResponseBody
    @PostMapping("/find")
    public List<TreatmentProtocolDto> findByDiseaseId(@RequestBody Map<String, Long> json){
        return treatmentProtocolService.getAllTreatmentProtocolsByDiseaseId(json.get("diseaseId"))
                .stream()
                .map(treatmentProtocolMapper::toDto)
                .collect(Collectors.toList());
    }



}
