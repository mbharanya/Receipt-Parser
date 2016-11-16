package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ch.bharanya.receipt_parser.export.ExcelExporter;
import ch.bharanya.receipt_parser.export.IExporter;
import ch.bharanya.receipt_parser.parser.Receipt;

public class App {
	public static void main(final String[] args) throws IOException {
		App app = new App();
		app.init();
	}

	private void init () throws IOException
	{
		IReceiptRetriever receiptRetriever = new CoopReceiptRetriever();
		List<Receipt> receipts = receiptRetriever.getReceipts();
		IExporter exporter = new ExcelExporter( new File(Config.getInstance().getProperty( "coop.export.file" )), receipts );
		exporter.export();
	}
}
