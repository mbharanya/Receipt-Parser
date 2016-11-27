package ch.bharanya.receipt_parser;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.Test;

import ch.bharanya.receipt_parser.retriever.coop.CoopImapMailRetriever;

public class ImapMailRetrieverTest {
	private final CoopImapMailRetriever receiver = new CoopImapMailRetriever();
	
	@Test
	public void testGetMails() throws MessagingException, IOException{
		receiver.getPdfReceipts();
	}
}
