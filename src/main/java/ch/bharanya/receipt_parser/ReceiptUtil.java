package ch.bharanya.receipt_parser;

import java.text.SimpleDateFormat;

import ch.bharanya.receipt_parser.parser.Receipt;

public class ReceiptUtil {
	
	public static String getMonthYearNameFromReceipt(final Receipt receipt){
		return new SimpleDateFormat("MM.YY").format(receipt.getDate());
	}
}
