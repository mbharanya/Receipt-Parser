package ch.bharanya.receipt_parser;

import java.util.Date;

import ch.bharanya.receipt_parser.parser.Receipt;

public class TestReceipt extends Receipt{
	public TestReceipt() {
		setId("05904 00378231 003 0001519");
		setDate(new Date());
		setTotalPrice(13.37D);
	}
}
