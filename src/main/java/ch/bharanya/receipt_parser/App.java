package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.bharanya.receipt_parser.export.ExcelExporter;
import ch.bharanya.receipt_parser.parser.Receipt;

public class App {
	/**
	 * <p>The {@link Logger} for this class.</p>
	 */
	private static final Logger LOG = LoggerFactory.getLogger( App.class ); 
	
	
	public static void main(final String[] args) throws IOException {
		App app = new App();
		app.init();
	}

	private void init () throws IOException
	{
		ReceiptRetrieverPool retrieverPool = new ReceiptRetrieverPool(
				new CoopReceiptRetriever()
		);
		List<Receipt> receipts = retrieverPool.getAllReceiptsFromAllRetrievers();
		LOG.info("Found {} receipts", receipts.size());
		
		ExporterPool exporterPool = new ExporterPool(
			new ExcelExporter( new File(Config.getInstance().getProperty( "coop.export.file" )), receipts )
		);
		

			
		exporterPool.executeExporters();
	}
}
