package ch.bharanya.receipt_parser;

import org.junit.Test;

public class ImapMailRetrieverTest {
	private final CoopImapMailRetriever receiver = new CoopImapMailRetriever();
	
	@Test
	public void testGetMails(){
		receiver.getMails();
	}
}
