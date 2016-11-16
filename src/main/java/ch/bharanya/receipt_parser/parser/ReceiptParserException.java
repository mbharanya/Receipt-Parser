package ch.bharanya.receipt_parser.parser;

import java.io.IOException;

public class ReceiptParserException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6819578697441843342L;

	public ReceiptParserException(final String message, final Exception exception) {
		super(message, exception);
	}
	
	public ReceiptParserException(final String message) {
		super(message);
	}

}
