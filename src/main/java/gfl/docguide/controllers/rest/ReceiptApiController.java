package gfl.docguide.controllers.rest;

import gfl.docguide.data.Receipt;
import gfl.docguide.data.dto.request.ReceiptRequest;
import gfl.docguide.services.ReceiptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
/**
 * Controller for handling receipt-related API requests.
 */
@RestController
@AllArgsConstructor
@RequestMapping(Api.V1 + "/receipt")
public class ReceiptApiController {
    private final ReceiptService receiptService;


    /**
     * Endpoint to submit receipt data and save the receipt document.
     * @param request The receipt request containing the data.
     * @return The text of the saved receipt document.
     */
    @ResponseBody
    @PostMapping("/submit-data")
    String submitReceipt(@RequestBody ReceiptRequest request){
        Receipt receipt = receiptService.createReceipt(request);
        return receiptService.saveReceiptDocument(receipt).getDocText();
    }
}
