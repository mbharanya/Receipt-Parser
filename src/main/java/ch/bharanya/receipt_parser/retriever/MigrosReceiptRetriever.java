package ch.bharanya.receipt_parser.retriever;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ch.bharanya.receipt_parser.config.Config;
import ch.bharanya.receipt_parser.parser.Receipt;
import ch.bharanya.receipt_parser.parser.ReceiptParserException;
import ch.bharanya.receipt_parser.parser.migros.MigrosOverviewCsvReceiptParser;

public class MigrosReceiptRetriever implements IReceiptRetriever {

	@Override
	public List<Receipt> getReceipts() {
		final List<Receipt> receipts = new ArrayList<>();

		final MigrosOverviewCsvReceiptParser parser = new MigrosOverviewCsvReceiptParser(new File(Config.getInstance().getProperty("migros.parser.csv.file")));
		try {
			final List<Receipt> parsedReceipts = parser.getReceipts();
			for (final Receipt parsedReceipt : parsedReceipts) {
				if (!ProcessedReceiptsStore.getInstance().hasReceiptBeenProcessed(parsedReceipt)) {
					ProcessedReceiptsStore.getInstance().addProcessedReceipt(parsedReceipt);
					receipts.add(parsedReceipt);
				}
			}

		} catch (final ReceiptParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return receipts;
	}

}
