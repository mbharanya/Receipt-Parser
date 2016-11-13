package ch.bharanya.receipt_parser.parser;

import java.io.IOException;
import java.util.Date;

public interface IReceiptParser {
	public double getTotalPrice() throws IOException;

	public Date getDate();
}
