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

import ch.bharanya.receipt_parser.parser.IReceiptParser;
import ch.bharanya.receipt_parser.parser.Receipt;
import ch.bharanya.receipt_parser.parser.ReceiptParserException;

public class MigrosOverviewCsvReceiptParser implements IReceiptParser {

	/**
	 * <p>
	 * The {@link Logger} for this class.
	 * </p>
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MigrosOverviewCsvReceiptParser.class);
	private final List<Receipt> receipts = new ArrayList<>();
	private final File file;
	private static final CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withDelimiter(';').withSkipHeaderRecord(true).withHeader(EMigrosOverviewCsvHeader.class);
	private static final String DATE_FORMAT = "dd.MM.yy HH:mm";

	public MigrosOverviewCsvReceiptParser(final File file) {
		this.file = file;
		try {
			loadFile();
		} catch (final IOException e) {
			LOGGER.error("Error parsing migros overview file", e);
		}
	}

	private void loadFile() throws IOException {
		final Reader fileReader = new FileReader(file);
		final CSVParser parser = new CSVParser(fileReader, CSV_FORMAT);

		parser.forEach(record -> {
			try {
				receipts.add(createMigrosReceipt(record));
			} catch (final ParseException e) {
				LOGGER.error("Error parsing receipt {}", record, e);
			}
		});

		parser.close();

	}

	private MigrosReceipt createMigrosReceipt(final CSVRecord record) throws ParseException {
		final DateFormat df = new SimpleDateFormat(DATE_FORMAT);

		final MigrosReceipt receipt = new MigrosReceipt();

		final String date = record.get(EMigrosOverviewCsvHeader.DATE);
		final String time = record.get(EMigrosOverviewCsvHeader.TIME);

		receipt.setDate(df.parse(date + " " + time));

		receipt.setLocation(record.get(EMigrosOverviewCsvHeader.LOCATION));
		receipt.setCheckOutNumber(Integer.valueOf(record.get(EMigrosOverviewCsvHeader.CHECKOUT_NUMBER)));
		receipt.setTransactionNumber(Long.valueOf(record.get(EMigrosOverviewCsvHeader.TRANSACTION_NUMBER)));
		receipt.setTotalPrice(Double.valueOf(record.get(EMigrosOverviewCsvHeader.TOTAL_PRICE)));
		receipt.setCumulusPoints(Double.valueOf(record.get(EMigrosOverviewCsvHeader.CUMULUS_POINTS)));

		return receipt;
	}

	@Override
	public List<Receipt> getReceipts() throws ReceiptParserException {
		return receipts;
	}

}
