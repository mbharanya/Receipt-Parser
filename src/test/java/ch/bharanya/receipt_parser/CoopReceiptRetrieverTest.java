package ch.bharanya.receipt_parser;

import org.junit.Test;

import ch.bharanya.receipt_parser.retriever.IReceiptRetriever;
import ch.bharanya.receipt_parser.retriever.coop.CoopReceiptRetriever;

public class CoopReceiptRetrieverTest
{
	private final IReceiptRetriever retriever = new CoopReceiptRetriever();
	
	@Test
	public void testGetReceipts () throws Exception
	{
		retriever.getReceipts();
	}
}
