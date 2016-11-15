package ch.bharanya.receipt_parser;

import org.junit.Test;

public class ImapMailRetrieverTest {
	private final ImapMailRetriever receiver = new ImapMailRetriever();
	@Test
	public void testGetMails(){
		receiver.getMails();
	}
}
