package ch.bharanya.receipt_parser.config;

public class ConfigException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6503750303303465123L;

	public ConfigException(final String msg, final Exception e) {
		super(msg, e);
	}
}
