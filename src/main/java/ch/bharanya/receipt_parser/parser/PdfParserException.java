package ch.bharanya.receipt_parser.parser;

public class PdfParserException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6819578697441843342L;

	public PdfParserException(final String message, final RuntimeException exception) {
		super(message, exception);
	}
	
	public PdfParserException(final String message) {
		super(message);
	}
}
