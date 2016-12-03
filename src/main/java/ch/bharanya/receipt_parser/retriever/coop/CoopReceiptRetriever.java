package ch.bharanya.receipt_parser.retriever.coop;

import java.io.File;
import java.util.List;

import ch.bharanya.receipt_parser.parser.IReceiptParser;
import ch.bharanya.receipt_parser.parser.Receipt;
import ch.bharanya.receipt_parser.parser.coop.CoopPdfReceiptParser;
import ch.bharanya.receipt_parser.retriever.IReceiptRetriever;

public class CoopReceiptRetriever implements IReceiptRetriever
{

	@Override
	public List<Receipt> getReceipts () throws Exception
	{
		final CoopImapMailRetriever mailRetriever = new CoopImapMailRetriever();
		final List<File> pdfReceipts = mailRetriever.getPdfReceipts();
		final IReceiptParser parser = new CoopPdfReceiptParser( pdfReceipts );

		return parser.getReceipts();
	}

}
