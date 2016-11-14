package ch.bharanya.receipt_parser;

import java.text.SimpleDateFormat;

import ch.bharanya.receipt_parser.parser.Receipt;

public class ReceiptUtil {
	
	public static String getMonthNameFromReceipt(final Receipt receipt){
		return new SimpleDateFormat("MMM").format(receipt.getDate());
	}
}
