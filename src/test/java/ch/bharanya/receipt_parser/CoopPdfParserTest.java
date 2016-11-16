package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import ch.bharanya.receipt_parser.parser.CoopPdfReceiptParser;
import ch.bharanya.receipt_parser.parser.IReceiptParser;
import junit.framework.Assert;

public class CoopPdfParserTest {
	private static final String RECEIPT_FILENAME = "U:\\Work\\Receipt Parser\\Coop Supermarkt_Gerlafingen_20161112_1533.pdf";

	IReceiptParser parser = new CoopPdfReceiptParser(new File(RECEIPT_FILENAME));

	@Test
	public void testGetTotalPrice() throws IOException {
		final double totalPrice = parser.getReceipt().getTotalPrice();
		Assert.assertEquals(28.40D, totalPrice);
	}

	@Test
	public void testGetDate() throws IOException, ParseException {
		final Date date = parser.getReceipt().getDate();
		final Date dateExpected;

		final String dateExpectedString = "12.11.16 15:33";
		final DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");

		dateExpected = df.parse(dateExpectedString);

		Assert.assertEquals(dateExpected, date);
	}
}
