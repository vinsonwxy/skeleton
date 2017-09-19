/**
 * This class is generated by jOOQ
 */
package generated.tables;


import generated.Public;
import generated.tables.records.ReceiptsTagsRecord;

import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReceiptsTags extends TableImpl<ReceiptsTagsRecord> {

	private static final long serialVersionUID = 1244297267;

	/**
	 * The reference instance of <code>public.receipts_tags</code>
	 */
	public static final ReceiptsTags RECEIPTS_TAGS = new ReceiptsTags();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ReceiptsTagsRecord> getRecordType() {
		return ReceiptsTagsRecord.class;
	}

	/**
	 * The column <code>public.receipts_tags.id</code>.
	 */
	public final TableField<ReceiptsTagsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>public.receipts_tags.merchant</code>.
	 */
	public final TableField<ReceiptsTagsRecord, String> MERCHANT = createField("merchant", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>public.receipts_tags.amount</code>.
	 */
	public final TableField<ReceiptsTagsRecord, BigDecimal> AMOUNT = createField("amount", org.jooq.impl.SQLDataType.DECIMAL.precision(12, 2), this, "");

	/**
	 * The column <code>public.receipts_tags.name</code>.
	 */
	public final TableField<ReceiptsTagsRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * Create a <code>public.receipts_tags</code> table reference
	 */
	public ReceiptsTags() {
		this("receipts_tags", null);
	}

	/**
	 * Create an aliased <code>public.receipts_tags</code> table reference
	 */
	public ReceiptsTags(String alias) {
		this(alias, RECEIPTS_TAGS);
	}

	private ReceiptsTags(String alias, Table<ReceiptsTagsRecord> aliased) {
		this(alias, aliased, null);
	}

	private ReceiptsTags(String alias, Table<ReceiptsTagsRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReceiptsTags as(String alias) {
		return new ReceiptsTags(alias, this);
	}

	/**
	 * Rename this table
	 */
	public ReceiptsTags rename(String name) {
		return new ReceiptsTags(name, null);
	}
}
