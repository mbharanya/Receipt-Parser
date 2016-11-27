package ch.bharanya.receipt_parser;

import org.junit.Test;

import ch.bharanya.receipt_parser.retriever.CoopReceiptRetriever;
import ch.bharanya.receipt_parser.retriever.IReceiptRetriever;

public class CoopReceiptRetrieverTest
{
	private IReceiptRetriever retriever = new CoopReceiptRetriever();
	
	@Test
	public void testGetReceipts ()
	{
		retriever.getReceipts();
	}
}
