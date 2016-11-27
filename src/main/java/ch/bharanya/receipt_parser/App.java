package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.bharanya.receipt_parser.config.Config;
import ch.bharanya.receipt_parser.export.excel.ExcelExporter;
import ch.bharanya.receipt_parser.export.excel.ExporterPool;
import ch.bharanya.receipt_parser.export.pushbullet.PushBulletExporter;
import ch.bharanya.receipt_parser.parser.Receipt;
import ch.bharanya.receipt_parser.retriever.CoopReceiptRetriever;
import ch.bharanya.receipt_parser.retriever.MigrosReceiptRetriever;
import ch.bharanya.receipt_parser.retriever.ReceiptRetrieverPool;

public class App {
	/**
	 * <p>
	 * The {@link Logger} for this class.
	 * </p>
	 */
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(final String[] args) throws IOException {
		final App app = new App();
		app.init();
	}

	private void init() throws IOException {
		final ReceiptRetrieverPool retrieverPool = new ReceiptRetrieverPool(new CoopReceiptRetriever(), new MigrosReceiptRetriever());
		final List<Receipt> receipts = retrieverPool.getAllReceiptsFromAllRetrievers();

		LOG.info("Found {} (new) receipt(s)", receipts.size());

		if (receipts.size() > 0){
			final ExporterPool exporterPool = new ExporterPool(
				new ExcelExporter(new File(Config.getInstance().getProperty("export.excel.file")), receipts),
				new PushBulletExporter(receipts)
			);
			exporterPool.executeExporters();
		}

		LOG.info("Done!");
	}
}
