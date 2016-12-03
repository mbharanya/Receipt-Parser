package ch.bharanya.receipt_parser.export;

public class ExportingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -346577764819491456L;

	public ExportingException(final String msg, final Exception e) {
		super(msg, e);
	}

	public ExportingException(final Exception e) {
		super(e);
	}
}
