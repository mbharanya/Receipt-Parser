package ch.bharanya.receipt_parser.retriever.migros;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.bharanya.receipt_parser.config.Config;
import ch.bharanya.receipt_parser.parser.Receipt;
import ch.bharanya.receipt_parser.parser.ReceiptParserException;
import ch.bharanya.receipt_parser.parser.migros.MigrosOverviewCsvReceiptParser;
import ch.bharanya.receipt_parser.retriever.IReceiptRetriever;

public class MigrosReceiptRetriever implements IReceiptRetriever {
	/**
	 * <p>
	 * The {@link Logger} for this class.
	 * </p>
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MigrosReceiptRetriever.class);

	@Override
	public List<Receipt> getReceipts() throws ReceiptParserException {
		final MigrosOverviewCsvReceiptParser parser = new MigrosOverviewCsvReceiptParser(new File(Config.getInstance().getProperty("migros.parser.csv.overview.file")));
		return parser.getReceipts();
	}

}
