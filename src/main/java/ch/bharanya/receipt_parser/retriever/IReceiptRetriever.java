package ch.bharanya.receipt_parser.retriever;

import java.util.List;

import ch.bharanya.receipt_parser.parser.Receipt;

public interface IReceiptRetriever {
	List<Receipt> getReceipts() throws Exception;
}
