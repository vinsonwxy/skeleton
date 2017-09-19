package dao;

import api.ReceiptResponse;
import api.ReceiptsTagsResponse;
import generated.tables.Receipts;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.ReceiptsTagsRecord;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class ReceiptDao {
    DSLContext dsl;

    public ReceiptDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public int insert(String merchantName, BigDecimal amount) {
        ReceiptsRecord receiptsRecord = dsl
                .insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT)
                .values(merchantName, amount)
                .returning(RECEIPTS.ID)
                .fetchOne();

        checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");

        return receiptsRecord.getId();
    }

    public List<ReceiptsTagsRecord> getAllReceipts() {
        Result<Record4<Integer, String, BigDecimal, String>> r =  dsl.select(RECEIPTS.ID, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT, TAGS.NAME).
                from(RECEIPTS.leftJoin(TAGS).on(RECEIPTS.ID.eq(TAGS.ID))).fetch();
        List<ReceiptsTagsRecord> ret_List = new ArrayList<>();
        for (Record4 r4: r){
            ReceiptsTagsRecord temp = new ReceiptsTagsRecord();
            temp.setId(r4.get(RECEIPTS.ID));
            temp.setMerchant(r4.get(RECEIPTS.MERCHANT));
            temp.setAmount(r4.get(RECEIPTS.AMOUNT));
            temp.setName(r4.get(TAGS.NAME));
            ret_List.add(temp);
        }
        return ret_List;
    }
}
