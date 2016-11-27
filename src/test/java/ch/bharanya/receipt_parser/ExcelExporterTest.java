package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import ch.bharanya.receipt_parser.export.IExporter;
import ch.bharanya.receipt_parser.export.excel.ExcelExporter;
import ch.bharanya.receipt_parser.parser.Receipt;

public class ExcelExporterTest {
	Receipt receipt = new Receipt("05904 00378231 003 0001519", new Date(), 13.37D);
	IExporter exporter = new ExcelExporter(new File("Abrechnig.xlsx"), receipt);

	@Test
	public void testExport() throws IOException {
		exporter.export();
	}
}
