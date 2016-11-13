package ch.bharanya.receipt_parser.parser;

import java.io.IOException;

public interface IReceiptParser {
	public double getTotalPrice() throws IOException;
}
