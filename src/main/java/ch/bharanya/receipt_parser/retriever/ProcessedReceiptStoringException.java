package ch.bharanya.receipt_parser.retriever;

public class ProcessedReceiptStoringException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2110590276492170092L;

	public ProcessedReceiptStoringException(final String msg, final Exception e) {
		super(msg, e);
	}
}
