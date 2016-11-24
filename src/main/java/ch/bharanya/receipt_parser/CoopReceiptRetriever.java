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

public class CoopReceiptRetriever implements IReceiptRetriever
{

	@Override
	public List<Receipt> getReceipts ()
	{
		final List<Receipt> receipts = new ArrayList<>();
		final CoopImapMailRetriever mailRetriever = new CoopImapMailRetriever();
		try
		{
			final List<File> pdfReceipts = mailRetriever.getPdfReceipts();
			final IReceiptParser parser = new CoopPdfReceiptParser( pdfReceipts );
			try
			{
				final List<Receipt> parsedReceipts = parser.getReceipts();
				for ( Receipt parsedReceipt : parsedReceipts )
				{
					if ( !ProcessedReceiptsStore.getInstance().hasReceiptBeenProcessed( parsedReceipt ) )
					{
						ProcessedReceiptsStore.getInstance().addProcessedReceipt( parsedReceipt );
						receipts.add( parsedReceipt );
					}
				}

			}
			catch ( final ReceiptParserException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
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
