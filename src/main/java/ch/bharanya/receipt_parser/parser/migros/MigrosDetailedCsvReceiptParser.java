package ch.bharanya.receipt_parser.parser.migros;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.bharanya.receipt_parser.config.Config;
import ch.bharanya.receipt_parser.parser.IReceiptParser;
import ch.bharanya.receipt_parser.parser.Receipt;
import ch.bharanya.receipt_parser.parser.ReceiptParserException;

public class MigrosDetailedCsvReceiptParser implements IReceiptParser {
	/**
	 * <p>
	 * The {@link Logger} for this class.
	 * </p>
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MigrosDetailedCsvReceiptParser.class);
	private static final CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withDelimiter(';');
	private static final String DATE_FORMAT = "MM.dd.YYYY HH:mm";
	private final File file;
	private final List<MigrosReceiptElement> receiptElements = new ArrayList<>();
	private List<Receipt> receipts;

	public MigrosDetailedCsvReceiptParser(final File file) {
		this.file = file;
		try {
			loadFile();
		} catch (final IOException e) {
			LOGGER.error("Error parsing detailed migros file", e);
		}
	}

	private void loadFile() throws IOException {
		final Reader fileReader = new FileReader(file);
		final CSVParser parser = new CSVParser(fileReader, CSV_FORMAT);

		parser.forEach(record -> {
			try {
				addRecordToReceiptElements(record);
			} catch (final ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		parser.close();

	}

	private void addRecordToReceiptElements(final CSVRecord record) throws ParseException {
		final DateFormat df = new SimpleDateFormat(DATE_FORMAT);

		final MigrosReceiptElement element = new MigrosReceiptElement();

		final String date = record.get(Config.getInstance().getProperty("migros.parser.csv.purchaseDateCol"));
		final String time = record.get(Config.getInstance().getProperty("migros.parser.csv.purchaseTimeCol"));

		element.setPurchaseDate(df.parse(date + time));

		element.setStoreName(record.get(Config.getInstance().getProperty("migros.parser.csv.storeNameCol")));
		element.setTransactionNumber(Long.valueOf(record.get(Config.getInstance().getProperty("migros.parser.csv.transactionNumberCol"))));
		element.setArticleName(record.get(Config.getInstance().getProperty("migros.parser.csv.articleNameCol")));
		element.setAmount(Double.valueOf(record.get(Config.getInstance().getProperty("migros.parser.csv.amountCol"))));
		element.setDiscount(Double.valueOf(record.get(Config.getInstance().getProperty("migros.parser.csv.discountCol"))));
		element.setPrice(Double.valueOf(record.get(Config.getInstance().getProperty("migros.parser.csv.priceCol"))));

		receiptElements.add(element);
	}

	@Override
	public List<Receipt> getReceipts() throws ReceiptParserException {
		receipts.add(new MigrosReceipt(receiptElements));
		return receipts;
	}

}
