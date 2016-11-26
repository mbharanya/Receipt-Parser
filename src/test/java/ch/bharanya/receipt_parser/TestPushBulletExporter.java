package ch.bharanya.receipt_parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ch.bharanya.receipt_parser.export.PushBulletExporter;
import ch.bharanya.receipt_parser.parser.Receipt;

public class TestPushBulletExporter {
	List<Receipt> receipts = Arrays.asList(new Receipt("1", new Date(), 11.13D));
	PushBulletExporter exporter = new PushBulletExporter(receipts);

	@Test
	public void testExport() throws IOException {
		exporter.export();
	}
}
