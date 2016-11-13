package ch.bharanya.receipt_parser.parser;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfParser implements IReceiptParser {
	private static final String TOTAL_PRICE_REGEX = "(Total CHF )(.*)";
	private static final int REGEX_MATCH_GROUP = 2;

	PDDocument document;
	File pdfFile;

	public PdfParser(final File file) {
		this.pdfFile = file;
		loadFile();
	}

	private void loadFile() {
		try {
			document = PDDocument.load(pdfFile);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void closeFile() throws IOException {
		if (document != null) {
			document.close();
		}
	}

	public double getTotalPrice() throws IOException {
		final PDFTextStripper stripper = new PDFTextStripper();
		final String allText = stripper.getText(document);
		closeFile();

		final Pattern pattern = Pattern.compile(TOTAL_PRICE_REGEX);
		final Matcher matcher = pattern.matcher(allText);
		try {
			return Double.parseDouble(matcher.group(REGEX_MATCH_GROUP));
		} catch (final IllegalStateException ise) {
			throw new PdfParserException("No total price found", ise);
		}

	}

}
