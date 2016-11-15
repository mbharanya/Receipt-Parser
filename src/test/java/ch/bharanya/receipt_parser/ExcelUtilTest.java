package ch.bharanya.receipt_parser;

import java.util.Date;

import org.junit.Test;

import ch.bharanya.receipt_parser.parser.Receipt;
import junit.framework.Assert;

public class ExcelUtilTest {
	@Test
	public void testGetSheetName() {
		final Date testDate = new Date();
		// 0 indexed ...
		testDate.setMonth(9);
		final Receipt receipt = new Receipt(testDate, 15.00);
		Assert.assertEquals("Einkaufen Okt", ExcelUtil.getSheetName(receipt));
	}
}
