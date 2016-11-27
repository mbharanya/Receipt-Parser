package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import ch.bharanya.receipt_parser.parser.IReceiptParser;
import ch.bharanya.receipt_parser.parser.ReceiptParserException;
import ch.bharanya.receipt_parser.parser.coop.CoopPdfReceiptParser;
import ch.bharanya.receipt_parser.parser.coop.CoopReceipt;
import junit.framework.Assert;

public class CoopPdfParserTest {
	private static final String RECEIPT_FILENAME = "Coop Supermarkt_Gerlafingen_20161112_1533.pdf";

	IReceiptParser parser = new CoopPdfReceiptParser(Arrays.asList( new File(RECEIPT_FILENAME)));

	@Test
	public void testGetTotalPrice() throws IOException {
		final double totalPrice = parser.getReceipts().get( 0 ).getTotalPrice();
		Assert.assertEquals(28.40D, totalPrice);
	}

	@Test
	public void testGetDate() throws IOException, ParseException {
		final Date date = parser.getReceipts().get( 0 ).getDate();
		final Date dateExpected;

		final String dateExpectedString = "12.11.16 15:33";
		final DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");

		dateExpected = df.parse(dateExpectedString);

		Assert.assertEquals(dateExpected, date);
	}
	
	@Test
	public void testGetLocation() throws ReceiptParserException{
		CoopReceipt receipt = (CoopReceipt) parser.getReceipts().get( 0 );
		Assert.assertEquals( "Gerlafingen", receipt.getLocation());
	}
}
