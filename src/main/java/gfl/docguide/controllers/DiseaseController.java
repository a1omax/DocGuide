package gfl.docguide.controllers;

import gfl.docguide.data.Disease;
import gfl.docguide.services.DiseaseService;
import gfl.docguide.services.SymptomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Controller
@RequestMapping("/disease")
public class DiseaseController {

    DiseaseService diseaseService;
    SymptomService symptomService;

    @GetMapping("/list")
    String showAllDiseases(Model model){
        model.addAttribute("diseaseList", diseaseService.getAllDiseases());
        return "disease/diseaseList";
    }

    @GetMapping("/create")
    String showCreateDiseaseForm(Model model){
        model.addAttribute("symptomList", symptomService.getAllSymptoms());
        return "disease/createDisease";
    }

    @PostMapping("/create")
    String createDisease(@RequestParam MultiValueMap<String, String> params){
        diseaseService.saveDiseaseFromParams(params);
        return "redirect:list";
    }

    @GetMapping("/update/{id}")
    String showDiseaseUpdateFrom(@PathVariable Long id, Model model){
        model.addAttribute("symptomList", symptomService.getAllSymptoms());
        model.addAttribute("disease", diseaseService.getDiseaseById(id).get());
        return "disease/updateDisease";
    }

    @PostMapping("/update")
    String updateDisease(@RequestParam MultiValueMap<String, String> params){
        diseaseService.updateDiseaseFromParams(params);
        return "redirect:list";
    }

    @PostMapping("/delete")
    String deleteDisease(@RequestParam Long id){
        diseaseService.deleteDiseaseById(id);
        return "redirect:list";
    }


    @PostMapping("/find")
    @ResponseBody
    public List<Disease> findDiseasesBySymptoms(@RequestBody Map<String, List<Long>> listMap){

        List<Long> symptomIds = listMap.get("symptomList");

        return diseaseService.findDiseasesBySymptomIds(symptomIds);

    }

}
