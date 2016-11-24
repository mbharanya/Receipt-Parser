package ch.bharanya.receipt_parser.parser;

import java.util.List;

public interface IReceiptParser {
	public List<Receipt> getReceipts() throws ReceiptParserException;
}
