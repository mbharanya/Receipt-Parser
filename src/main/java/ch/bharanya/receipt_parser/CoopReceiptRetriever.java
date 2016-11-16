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
	
	
	public List<Receipt> getReceipts() {
		List<Receipt> receipts = new ArrayList<>();
		CoopImapMailRetriever mailRetriever = new CoopImapMailRetriever();
		try
		{
			List<File> pdfReceipts = mailRetriever.getPdfReceipts();
			for (File pdfReceipt : pdfReceipts){
				IReceiptParser parser = new CoopPdfReceiptParser( pdfReceipt );
				try
				{
					receipts.add( parser.getReceipt() );
				}
				catch ( ReceiptParserException e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
		catch ( MessagingException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return receipts;
	}

}
