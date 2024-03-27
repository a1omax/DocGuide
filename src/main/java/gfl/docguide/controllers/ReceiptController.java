package gfl.docguide.controllers;


import gfl.docguide.services.ReceiptService;
import gfl.docguide.services.SymptomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle requests related to receipts.
 */
@AllArgsConstructor
@Controller
public class ReceiptController {

    ReceiptService receiptService;
    SymptomService symptomService;


    /**
     * Redirects to the receipt page.
     * @return Redirects to the receipt page.
     */
    @GetMapping("/")
    String index(){
        return "redirect:/receipt";
    }

    /**
     * Displays the receipt form page.
     * @param model The model to be populated with symptom data.
     * @return The view name for displaying the receipt form page.
     */
    @GetMapping("/receipt")
    String showReceiptFormPage(Model model){

        model.addAttribute("symptomList", symptomService.getAllSymptoms());
        return "mainPage";
    }



}
