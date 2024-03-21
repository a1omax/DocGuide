package gfl.docguide.controllers;


import gfl.docguide.components.Receipt;
import gfl.docguide.data.dto.request.ReceiptRequest;
import gfl.docguide.services.ReceiptService;
import gfl.docguide.services.SymptomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
public class ReceiptController {

    ReceiptService receiptService;
    SymptomService symptomService;

    @GetMapping("/")
    String index(){
        return "redirect:/receipt";
    }


    @GetMapping("/receipt")
    String showReceiptFormPage(Model model){

        model.addAttribute("symptomList", symptomService.getAllSymptoms());
        return "mainPage";
    }
    @ResponseBody
    @PostMapping("/receipt/submit-data")
    String submitReceipt(@RequestBody ReceiptRequest request){
        Receipt receipt = receiptService.createReceipt(request);

        return receipt.toTextReceipt();
    }


}
