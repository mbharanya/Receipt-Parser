package ch.bharanya.receipt_parser.parser;

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

public class MigrosOverviewCsvReceiptParser implements IReceiptParser
{
	private final List<Receipt> receipts = new ArrayList<>();
	private final File file;
	private static final CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withDelimiter( ';' ).withSkipHeaderRecord(true).withHeader(EMigrosOverviewCsvHeader.class);
	private static final String DATE_FORMAT = "dd.MM.yy HH:mm";

	public MigrosOverviewCsvReceiptParser ( final File file )
	{
		this.file = file;
		try
		{
			loadFile();
		}
		catch ( final IOException e )
		{
			e.printStackTrace();
		}
	}

	private void loadFile () throws IOException
	{
		final Reader fileReader = new FileReader(file);
		final CSVParser parser = new CSVParser(fileReader, CSV_FORMAT);
		
		parser.forEach(record -> {
			try
			{
				receipts.add(createMigrosReceipt(record));
			}
			catch ( final ParseException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		parser.close();

	}

	private MigrosReceipt createMigrosReceipt ( final CSVRecord record ) throws ParseException
	{
		final DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		
		final MigrosReceipt receipt = new MigrosReceipt();
		
		final String date = record.get( EMigrosOverviewCsvHeader.DATE);
		final String time = record.get( EMigrosOverviewCsvHeader.TIME);

		receipt.setDate(df.parse( date+" "+time ));
		
		receipt.setLocation(record.get(EMigrosOverviewCsvHeader.LOCATION));
		receipt.setCheckOutNumber(Integer.valueOf(record.get( EMigrosOverviewCsvHeader.CHECKOUT_NUMBER)));
		receipt.setTransactionNumber(Long.valueOf(record.get(EMigrosOverviewCsvHeader.TRANSACTION_NUMBER)));
		receipt.setTotalPrice(Double.valueOf( record.get(EMigrosOverviewCsvHeader.TOTAL_PRICE)));
		receipt.setCumulusPoints(Double.valueOf( record.get(EMigrosOverviewCsvHeader.CUMULUS_POINTS)));
		
		return receipt;

//		
//		final String date = record.get( Config.getInstance().getProperty( "migros.parser.csv.overview.dateCol" ));
//		final String time = record.get( Config.getInstance().getProperty( "migros.parser.csv.overview.timeCol" ));
//		
//		receipt.setDate(df.parse( date+" "+time ));
//		
//		receipt.setLocation(Config.getInstance().getProperty("migros.parser.csv.overview.locationCol"));
//		receipt.setCheckOutNumber(Integer.valueOf( Config.getInstance().getProperty("migros.parser.csv.overview.checkOutNumberCol")));
//		receipt.setTransactionNumber(Long.valueOf( Config.getInstance().getProperty("migros.parser.csv.overview.transactionNumberCol")));
//		receipt.setTotalPrice(Double.valueOf( Config.getInstance().getProperty("migros.parser.csv.overview.totalPriceCol")));
//		receipt.setCumulusPoints(Double.valueOf( Config.getInstance().getProperty("migros.parser.csv.overview.cumulusPointsCol")));
//		
	}

	@Override
	public List<Receipt> getReceipts () throws ReceiptParserException
	{
		return receipts;
	}

}
