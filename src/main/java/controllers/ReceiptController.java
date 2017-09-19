package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import api.ReceiptsTagsResponse;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.ReceiptsTagsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/receipts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptController {
    final ReceiptDao receipts;

    public ReceiptController(ReceiptDao receipts) {
        this.receipts = receipts;
    }

    @POST
    public int createReceipt(@Valid @NotNull CreateReceiptRequest receipt) {
        return receipts.insert(receipt.merchant, receipt.amount);
    }

    @GET
    public List<ReceiptsTagsResponse> getReceipts() {
        List<ReceiptsTagsRecord> receiptsTagsRecords = receipts.getAllReceipts();
        return receiptsTagsRecords.stream().map(ReceiptsTagsResponse::new).collect(toList());
    }
}
