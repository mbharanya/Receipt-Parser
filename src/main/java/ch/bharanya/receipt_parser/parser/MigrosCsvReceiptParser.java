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

public class MigrosCsvReceiptParser implements IReceiptParser
{
	private static final CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withDelimiter(';');
	private static final String DATE_FORMAT = "MM.dd.YYYY HH:mm";
	private File file;
	private List<MigrosReceiptElement> receiptElements = new ArrayList<>();
	private MigrosReceipt receipt;

	public MigrosCsvReceiptParser (File file)
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
				addRecordToReceiptElements(record);
			}
			catch ( ParseException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		parser.close();

	}

	private void addRecordToReceiptElements ( CSVRecord record ) throws ParseException
	{
		final DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		
		MigrosReceiptElement element = new MigrosReceiptElement();
		
		String date = record.get( Config.getInstance().getProperty( "migros.parser.csv.purchaseDateCol" ));
		String time = record.get( Config.getInstance().getProperty( "migros.parser.csv.purchaseTimeCol" ));
		
		element.setPurchaseDate(df.parse( date+time ));
		
		element.setStoreName(record.get( Config.getInstance().getProperty( "migros.parser.csv.storeNameCol" )));
		element.setTransactionNumber(Long.valueOf( record.get( Config.getInstance().getProperty( "migros.parser.csv.transactionNumberCol" ))));
		element.setArticleName(record.get( Config.getInstance().getProperty( "migros.parser.csv.articleNameCol" )));
		element.setAmount(Double.valueOf(record.get( Config.getInstance().getProperty( "migros.parser.csv.amountCol" ))));
		element.setDiscount(Double.valueOf(record.get( Config.getInstance().getProperty( "migros.parser.csv.discountCol" ))));
		element.setPrice(Double.valueOf( record.get( Config.getInstance().getProperty( "migros.parser.csv.priceCol" ))));
		
		receiptElements.add( element );
	}

	@Override
	public Receipt getReceipt () throws ReceiptParserException
	{
		receipt.setReceiptElements( receiptElements );
		return receipt;
	}

}
