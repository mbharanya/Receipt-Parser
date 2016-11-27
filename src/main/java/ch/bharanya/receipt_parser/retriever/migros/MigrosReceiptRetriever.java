package ch.bharanya.receipt_parser.retriever.migros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.bharanya.receipt_parser.config.Config;
import ch.bharanya.receipt_parser.parser.Receipt;
import ch.bharanya.receipt_parser.parser.ReceiptParserException;
import ch.bharanya.receipt_parser.parser.migros.MigrosOverviewCsvReceiptParser;
import ch.bharanya.receipt_parser.retriever.IReceiptRetriever;
import ch.bharanya.receipt_parser.retriever.ProcessedReceiptsStore;

public class MigrosReceiptRetriever implements IReceiptRetriever {
	/**
	 * <p>
	 * The {@link Logger} for this class.
	 * </p>
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MigrosReceiptRetriever.class);

	@Override
	public List<Receipt> getReceipts() {
		final List<Receipt> receipts = new ArrayList<>();

		final MigrosOverviewCsvReceiptParser parser = new MigrosOverviewCsvReceiptParser(new File(Config.getInstance().getProperty("migros.parser.csv.overview.file")));
		try {
			final List<Receipt> parsedReceipts = parser.getReceipts();
			for (final Receipt parsedReceipt : parsedReceipts) {
				if (!ProcessedReceiptsStore.getInstance().hasReceiptBeenProcessed(parsedReceipt)) {
					ProcessedReceiptsStore.getInstance().addProcessedReceipt(parsedReceipt);
					receipts.add(parsedReceipt);
				}
			}

		} catch (final ReceiptParserException e) {
			LOG.error("Error parsing migros Receipt", e);
		}

		return receipts;
	}

}
