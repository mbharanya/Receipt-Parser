package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import ch.bharanya.receipt_parser.parser.CoopPdfReceiptParser;
import ch.bharanya.receipt_parser.parser.IReceiptParser;
import ch.bharanya.receipt_parser.parser.Receipt;
import ch.bharanya.receipt_parser.parser.ReceiptParserException;

public class CoopReceiptRetriever implements IReceiptRetriever{
	
	
	@Override
	public List<Receipt> getReceipts() {
		final List<Receipt> receipts = new ArrayList<>();
		final CoopImapMailRetriever mailRetriever = new CoopImapMailRetriever();
		try
		{
			final List<File> pdfReceipts = mailRetriever.getPdfReceipts();
			for (final File pdfReceipt : pdfReceipts){
				final IReceiptParser parser = new CoopPdfReceiptParser( pdfReceipt );
				try
				{
					final Receipt receipt = parser.getReceipt();
					if (!ProcessedReceiptsStore.getInstance().hasReceiptBeenProcessed(receipt)){
						ProcessedReceiptsStore.getInstance().addProcessedReceipt(receipt);
						receipts.add( receipt );
					}
				}
				catch ( final ReceiptParserException e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
		catch ( final MessagingException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( final IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return receipts;
	}

}
