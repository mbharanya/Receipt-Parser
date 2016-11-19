package ch.bharanya.receipt_parser.parser;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


	PDDocument document;
	File pdfFile;
	private String allText;

	public CoopPdfReceiptParser(final File file) {
		this.pdfFile = file;
		try {
			loadFile();
			readText();
			closeFile();
		} catch (final IOException e) {
			LOG.error( "Error parsing coop pdf" );
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Receipt getReceipt () throws ReceiptParserException
	{
 		return new CoopReceipt(
 			getId(),
			getDate(),
			getTotalPrice(),
			getLocation()
		);
	}

	private void loadFile() throws ReceiptParserException {
		try {
			LOG.info( "Loading file {} to parse", pdfFile.getName() );
			document = PDDocument.load(pdfFile);
		} catch (final Exception e) {
			throw new ReceiptParserException("can't load pdf", e);
		}

	}

	private void readText() throws IOException {
		final PDFTextStripper stripper = new PDFTextStripper();
		allText = stripper.getText(document);
	}
	
	private String getId() throws ReceiptParserException {
		return getMatchedString(ID_REGEX, ID_REGEX_MATCH_GROUP, allText, "id");
	}

	private double getTotalPrice() throws ReceiptParserException {
		return Double.parseDouble(getMatchedString(TOTAL_PRICE_REGEX, TOTAL_PRICE_REGEX_MATCH_GROUP, allText, "price"));
	}

	private Date getDate() throws ReceiptParserException {
		final DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		try {
			return df.parse(getMatchedString(DATE_REGEX, DATE_REGEX_MATCH_GROUP, allText, "date"));
		} catch (final ParseException pe) {
			throw new ReceiptParserException("Couldn't convert matched String to Date", pe);
		}
	}
	
	private String getLocation() throws ReceiptParserException{
		final String fileName = pdfFile.getName();
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

	private void closeFile() throws IOException {
		if (document != null) {
			document.close();
		}
	}

}
