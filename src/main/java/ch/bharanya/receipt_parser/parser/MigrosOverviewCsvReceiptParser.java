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

import ch.bharanya.receipt_parser.Config;

public class MigrosOverviewCsvReceiptParser implements IReceiptParser
{
	private List<Receipt> receipts = new ArrayList<>();
	private File file;
	private static final CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withDelimiter( ';' ).withSkipHeaderRecord( true );
	private static final String DATE_FORMAT = "MM.dd.YYYY HH:mm";

	public MigrosOverviewCsvReceiptParser ( File file )
	{
		this.file = file;
		try
		{
			loadFile();
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
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
				createMigrosReceipt(record);
			}
			catch ( ParseException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		parser.close();

	}

	private void createMigrosReceipt ( CSVRecord record ) throws ParseException
	{
		final DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		
		MigrosReceipt receipt = new MigrosReceipt();
		
		String date = record.get( Config.getInstance().getProperty( "migros.parser.csv.overview.dateCol" ));
		String time = record.get( Config.getInstance().getProperty( "migros.parser.csv.overview.timeCol" ));
		
		receipt.setDate(df.parse( date+" "+time ));
		
		receipt.setLocation(Config.getInstance().getProperty("migros.parser.csv.overview.locationCol"));
		receipt.setCheckOutNumber(Integer.valueOf( Config.getInstance().getProperty("migros.parser.csv.overview.checkOutNumberCol")));
		receipt.setTransactionNumber(Long.valueOf( Config.getInstance().getProperty("migros.parser.csv.overview.transactionNumberCol")));
		receipt.setTotalPrice(Double.valueOf( Config.getInstance().getProperty("migros.parser.csv.overview.totalPriceCol")));
		receipt.setCumulusPoints(Double.valueOf( Config.getInstance().getProperty("migros.parser.csv.overview.cumulusPointsCol")));
		
	}

	@Override
	public List<Receipt> getReceipts () throws ReceiptParserException
	{
		return receipts;
	}

}
