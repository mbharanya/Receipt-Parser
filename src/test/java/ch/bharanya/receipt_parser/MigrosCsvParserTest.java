package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import ch.bharanya.receipt_parser.parser.CoopReceipt;
import ch.bharanya.receipt_parser.parser.IReceiptParser;
import ch.bharanya.receipt_parser.parser.MigrosOverviewCsvReceiptParser;
import ch.bharanya.receipt_parser.parser.ReceiptParserException;
import junit.framework.Assert;

public class MigrosCsvParserTest
{
	IReceiptParser parser = new MigrosOverviewCsvReceiptParser( new File("cumulus-sales-slip-overview.csv") );
	
	@Test
	public void testGetTotalPrice() throws IOException {
		final double totalPrice = parser.getReceipts().get( 0 ).getTotalPrice();
		Assert.assertEquals(5.7D, totalPrice);
	}

	@Test
	public void testGetDate() throws IOException, ParseException {
		final Date date = parser.getReceipts().get( 0 ).getDate();
		final Date dateExpected;

		final String dateExpectedString = "22.11.16 12:38";
		final DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");

		dateExpected = df.parse(dateExpectedString);

		Assert.assertEquals(dateExpected, date);
	}
	
	@Test
	public void testGetLocation() throws ReceiptParserException{
		CoopReceipt receipt = (CoopReceipt) parser.getReceipts().get( 0 );
		Assert.assertEquals( "AA M Biberist", receipt.getLocation());
	}
}
