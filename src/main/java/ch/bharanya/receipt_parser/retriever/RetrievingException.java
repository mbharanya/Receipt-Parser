package ch.bharanya.receipt_parser.retriever;

public class RetrievingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8275501394101361178L;

	public RetrievingException(final String msg, final Exception e) {
		super(msg, e);
	}

	public RetrievingException(final Exception e) {
		super(e);
	}
}
