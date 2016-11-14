package ch.bharanya.receipt_parser;

import java.util.Date;

import org.junit.Test;

import ch.bharanya.receipt_parser.parser.Receipt;
import junit.framework.Assert;

public class ExcelUtilTest {
	@Test
	public void testGetSheetName() {
		final Receipt receipt = new Receipt(new Date(), 15.00);
		Assert.assertEquals("Einkaufen Nov", ExcelUtil.getSheetName(receipt));
	}
}
