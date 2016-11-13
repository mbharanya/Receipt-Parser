package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import ch.bharanya.receipt_parser.parser.IReceiptParser;
import ch.bharanya.receipt_parser.parser.PdfParser;
import junit.framework.Assert;

public class PdfParserTest {
	private static final String RECEIPT_FILENAME = "U:\\Work\\Receipt Parser\\Coop Supermarkt_Gerlafingen_20161112_1533.pdf";

	IReceiptParser parser = new PdfParser(new File(RECEIPT_FILENAME));
	@Test
	public void testParse() throws IOException{
		final double totalPrice = parser.getTotalPrice();
		Assert.assertEquals(28.40D, totalPrice);
	}
}
