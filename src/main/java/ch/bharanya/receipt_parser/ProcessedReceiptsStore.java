package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import ch.bharanya.receipt_parser.parser.Receipt;

public class ProcessedReceiptsStore {
	private static final CSVFormat CSV_FORMAT = CSVFormat.EXCEL;
	final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final int ID_COL_INDEX = 0;
	private static final int DATE_COL_INDEX = 1;
	private static final int TOTAL_PRICE_COL_INDEX = 2;
	List<Receipt> processsedReceipts = new ArrayList<>();
	private File storeFile;

	public ProcessedReceiptsStore() throws IOException {
		load();
	}
	
	private void load() throws IOException {
		storeFile = new File(Config.getInstance().getProperty("store.file"));
		if (!storeFile.exists()){
			storeFile.createNewFile();
		}
		final Reader receiptStoreFileReader = new FileReader(storeFile);
		final CSVParser parser = new CSVParser(receiptStoreFileReader, CSV_FORMAT);
		parser.forEach(record -> addCSVRecordToProcessedReceipts(record));
		parser.close();
	}
	
	public void addProcessedReceipt(final Receipt receipt){
		processsedReceipts.add(receipt);
		try {
			writeReceiptToFile(receipt);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//TODO: handle open / close better
	private void writeReceiptToFile(final Receipt receipt) throws IOException {
		final FileWriter fileWriter = new FileWriter(storeFile);

		final CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSV_FORMAT);

		final List<String> values = new ArrayList<>();
		values.add(receipt.getId());
		values.add(dateFormat.format(receipt.getDate()));
		values.add(Double.toString(receipt.getTotalPrice()));

		csvPrinter.print(values);
		csvPrinter.println();
		csvPrinter.close();
	}
	
	private void addCSVRecordToProcessedReceipts(final CSVRecord csvRecord) {
		final String id = csvRecord.get(ID_COL_INDEX);
		Date date = null;
		try {
			date = dateFormat.parse(csvRecord.get(DATE_COL_INDEX));
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		final double totalPrice = Double.valueOf(csvRecord.get(TOTAL_PRICE_COL_INDEX));
		
		addProcessedReceipt(new Receipt(id, date, totalPrice));
	}

	public boolean hasReceiptBeenProcessed(final Receipt receipt){
		for (final Receipt processsedReceipt : processsedReceipts) {
			if(processsedReceipt.getId().equals(receipt.getId())){
				return true;
			}
		}
		return false;
	}
}
