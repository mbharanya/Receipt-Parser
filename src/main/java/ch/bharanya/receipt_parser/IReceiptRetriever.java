package ch.bharanya.receipt_parser;

import java.util.List;

import ch.bharanya.receipt_parser.parser.Receipt;

interface IReceiptRetriever {
	List<Receipt> getReceipts();
}
