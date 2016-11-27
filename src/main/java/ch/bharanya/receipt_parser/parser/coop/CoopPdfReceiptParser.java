package ch.bharanya.receipt_parser.parser.coop;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.bharanya.receipt_parser.parser.IReceiptParser;
import ch.bharanya.receipt_parser.parser.Receipt;
import ch.bharanya.receipt_parser.parser.ReceiptParserException;

public class CoopPdfReceiptParser implements IReceiptParser {
	/**
	 * <p>The {@link Logger} for this class.</p>
	 */
	private static final Logger LOG = LoggerFactory.getLogger( CoopPdfReceiptParser.class ); 

	private static final String TOTAL_PRICE_REGEX = "(Total CHF )(.*)";
	private static final int TOTAL_PRICE_REGEX_MATCH_GROUP = 2;
	
	private static final String DATE_FORMAT = "dd.MM.yy HH:mm";
	// horrible way to match the date
	private static final String DATE_REGEX = "[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}\\s[0-9]{2}\\:[0-9]{2}";
	private static final int DATE_REGEX_MATCH_GROUP = 0;
	
	private static final String LOCATION_FILENAME_REGEX = "(Coop\\sSupermarkt)_([a-zA-Z]+)_";
	private static final int LOCATION_FILENAME_REGEX_MATCH_GROUP = 2;

	private static final String ID_REGEX = "[0-9]{5}\\s[0-9]{8}\\s[0-9]{3}\\s[0-9]{7}";
	private static final int ID_REGEX_MATCH_GROUP = 0;


	List<CoopPdfReceiptParsingData> parsingDatas = new ArrayList<>();
	
	private List<Receipt> receipts = new ArrayList<>();

	public CoopPdfReceiptParser(final List<File> files) {
		try {
			for (File pdfFile : files){
				CoopPdfReceiptParsingData parsingData = new CoopPdfReceiptParsingData(pdfFile);
				parsingData.setDocument( getPDDocument( pdfFile ) );
				parsingData.setText( getText( parsingData ) );
				
				getText (parsingData);
				parsingDatas.add( parsingData );
				closeFile(parsingData);
			}
		} catch (final IOException e) {
			LOG.error( "Error parsing coop pdf",e );
		}
	}
	
	@Override
	public List<Receipt> getReceipts () throws ReceiptParserException
	{
		for(CoopPdfReceiptParsingData parsingData : parsingDatas){
	 		receipts.add( new CoopReceipt(
	 			getId(parsingData),
				getDate(parsingData),
				getTotalPrice(parsingData),
				getLocation(parsingData)
			));
		}
		return receipts;
	}

	private PDDocument getPDDocument(File pdfFile) throws ReceiptParserException {
		try {
			LOG.info( "Loading file {} to parse", pdfFile.getName() );
			return  PDDocument.load(pdfFile);
		} catch (final Exception e) {
			throw new ReceiptParserException("can't load pdf "+pdfFile.getName(), e);
		}
	}

	private String getText(CoopPdfReceiptParsingData parsingData) throws IOException {
		final PDFTextStripper stripper = new PDFTextStripper();
		return stripper.getText(parsingData.getDocument());
	}
	
	private String getId(CoopPdfReceiptParsingData parsingData) throws ReceiptParserException {
		return getMatchedString(ID_REGEX, ID_REGEX_MATCH_GROUP, parsingData.getText(), "id");
	}

	private double getTotalPrice(CoopPdfReceiptParsingData parsingData) throws ReceiptParserException {
		return Double.parseDouble(getMatchedString(TOTAL_PRICE_REGEX, TOTAL_PRICE_REGEX_MATCH_GROUP, parsingData.getText(), "price"));
	}

	private Date getDate(CoopPdfReceiptParsingData parsingData) throws ReceiptParserException {
		final DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		try {
			return df.parse(getMatchedString(DATE_REGEX, DATE_REGEX_MATCH_GROUP, parsingData.getText(), "date"));
		} catch (final ParseException pe) {
			throw new ReceiptParserException("Couldn't convert matched String to Date", pe);
		}
	}
	
	private String getLocation(CoopPdfReceiptParsingData parsingData) throws ReceiptParserException{
		final String fileName = parsingData.getFile().getName();
		return getMatchedString(LOCATION_FILENAME_REGEX, LOCATION_FILENAME_REGEX_MATCH_GROUP, fileName, "location");
	}
	
	private String getMatchedString(final String regex, final int regexMatchGroup, final String target, final String searchTopic) throws ReceiptParserException{
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(target);
		try {
			if (matcher.find()) {
				return matcher.group(regexMatchGroup);
			} else {
				throw new ReceiptParserException("No "+searchTopic+" found");
			}
		} catch (final IllegalStateException ise) {
			throw new ReceiptParserException("General Parsing error while parsing"+searchTopic, ise);
		}
	}

	private void closeFile(CoopPdfReceiptParsingData parsingData) throws IOException {
		PDDocument document = parsingData.getDocument();
		if (document != null) {
			document.close();
		}
	}

}
