package ch.bharanya.receipt_parser.retriever;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.bharanya.receipt_parser.parser.Receipt;

public class ReceiptRetrieverPool
{
	private List<IReceiptRetriever> retrievers = new ArrayList<>();
	
	public ReceiptRetrieverPool (IReceiptRetriever... retrievers)
	{
		this.retrievers.addAll( Arrays.asList( retrievers ) );
	}
	
	
	public List<Receipt> getAllReceiptsFromAllRetrievers() throws IOException{
		List<Receipt> receipts = new ArrayList<>();
		
		for ( IReceiptRetriever retriever : retrievers )
		{
			receipts.addAll( retriever.getReceipts());
		}
		return receipts;
	}

}
