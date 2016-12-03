package ch.bharanya.receipt_parser.retriever;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.bharanya.receipt_parser.parser.Receipt;

public class ReceiptRetrieverPool
{
	private final List<IReceiptRetriever> retrievers = new ArrayList<>();
	
	public ReceiptRetrieverPool (final IReceiptRetriever... retrievers)
	{
		this.retrievers.addAll( Arrays.asList( retrievers ) );
	}
	
	
	public List<Receipt> getAllReceiptsFromAllRetrievers() throws RetrievingException{
		final List<Receipt> receipts = new ArrayList<>();
		
		for ( final IReceiptRetriever retriever : retrievers )
		{
			try {
				receipts.addAll( retriever.getReceipts());
			} catch (final Exception e) {
				throw new RetrievingException(e);
			}
		}
		return receipts;
	}

}
