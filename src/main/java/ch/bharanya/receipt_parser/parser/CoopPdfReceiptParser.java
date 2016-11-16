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

public class CoopPdfReceiptParser implements IReceiptParser {
	private static final String DATE_FORMAT = "dd.MM.yy HH:mm";
	private static final String TOTAL_PRICE_REGEX = "(Total CHF )(.*)";
	private static final int TOTAL_PRICE_REGEX_MATCH_GROUP = 2;
	// horrible way to match the date
	private static final String DATE_REGEX = "[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}\\s[0-9]{2}\\:[0-9]{2}";
	private static final int DATE_REGEX_MATCH_GROUP = 0;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Receipt getReceipt () throws ReceiptParserException
	{
		return new CoopReceipt(
			getDate(),
			getTotalPrice()
		);
	}

	private void loadFile() throws ReceiptParserException {
		try {
			document = PDDocument.load(pdfFile);
		} catch (final Exception e) {
			throw new ReceiptParserException("can't load pdf", e);
		}

	}

	private void readText() throws IOException {
		final PDFTextStripper stripper = new PDFTextStripper();
		allText = stripper.getText(document);
	}

	private double getTotalPrice() throws ReceiptParserException {
		final Pattern pattern = Pattern.compile(TOTAL_PRICE_REGEX);
		final Matcher matcher = pattern.matcher(allText);
		try {
			if (matcher.find()) {
				return Double.parseDouble(matcher.group(TOTAL_PRICE_REGEX_MATCH_GROUP));
			} else {
				throw new ReceiptParserException("No total price found");
			}
		} catch (final IllegalStateException ise) {
			throw new ReceiptParserException("General Parsing error", ise);
		}
	}

	// TODO: refactor redundant matching code
	private Date getDate() throws ReceiptParserException {
		final Pattern pattern = Pattern.compile(DATE_REGEX);
		final Matcher matcher = pattern.matcher(allText);
		final DateFormat df = new SimpleDateFormat(DATE_FORMAT);

		try {
			if (matcher.find()) {
				return df.parse(matcher.group(DATE_REGEX_MATCH_GROUP));
			} else {
				throw new ReceiptParserException("No date found");
			}
		} catch (final IllegalStateException ise) {
			throw new ReceiptParserException("General Parsing error", ise);
		} catch (final ParseException pe) {
			throw new ReceiptParserException("Couldn't convert matched String to Date", pe);
		}
	}

	private void closeFile() throws IOException {
		if (document != null) {
			document.close();
		}
	}

}
