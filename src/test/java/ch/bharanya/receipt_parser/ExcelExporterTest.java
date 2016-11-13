package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import ch.bharanya.receipt_parser.parser.Receipt;

public class ExcelExporterTest {
	Receipt receipt = new Receipt(new Date(), 13.37D);
	IExporter exporter = new ExcelExporter(new File("Abrechnig.xlsx"), receipt);

	@Test
	public void testExport() throws IOException {
		exporter.export();
	}
}
