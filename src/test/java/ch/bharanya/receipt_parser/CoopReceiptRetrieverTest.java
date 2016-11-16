package ch.bharanya.receipt_parser;

import org.junit.Test;

public class CoopReceiptRetrieverTest
{
	private IReceiptRetriever retriever = new CoopReceiptRetriever();
	
	@Test
	public void testGetReceipts ()
	{
		retriever.getReceipts();
	}
}
