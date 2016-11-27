package ch.bharanya.receipt_parser.export.excel;
import java.util.Formatter;
import java.util.Locale;

import ch.bharanya.receipt_parser.ReceiptUtil;
import ch.bharanya.receipt_parser.parser.Receipt;

public class ExcelUtil {
	private static final String SHEET_NAME_TEMPLATE = "Einkaufen %s";

	public static String getSheetName(final Receipt receipt) {
		final StringBuilder sb = new StringBuilder();
		// Send all output to the Appendable object sb
		final Formatter formatter = new Formatter(sb, Locale.GERMAN);

		return formatter.format(SHEET_NAME_TEMPLATE, ReceiptUtil.getMonthNameFromReceipt(receipt)).toString();
	}
}
