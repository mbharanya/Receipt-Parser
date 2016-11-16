package ch.bharanya.receipt_parser.parser;

public interface IReceiptParser {
	public Receipt getReceipt() throws ReceiptParserException;
}
