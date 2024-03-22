package gfl.docguide.controllers.rest;

import gfl.docguide.data.Receipt;
import gfl.docguide.data.dto.request.ReceiptRequest;
import gfl.docguide.services.ReceiptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(Api.V1 + "/receipt")
public class ReceiptApiController {
    private final ReceiptService receiptService;

    @ResponseBody
    @PostMapping("/submit-data")
    String submitReceipt(@RequestBody ReceiptRequest request){
        Receipt receipt = receiptService.createReceipt(request);
        return receiptService.saveReceiptDocument(receipt).getDocText();
    }
}
