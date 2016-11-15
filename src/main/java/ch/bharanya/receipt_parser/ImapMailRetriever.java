package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class ImapMailRetriever {

	public void getMails() {
		final Properties properties = new Properties();
		final Properties credentialProperties = new Properties();
		try {
			properties.load(new FileInputStream(new File("config.properties")));
			credentialProperties.load(new FileInputStream(new File("credentials.properties")));

			final Session session = Session.getDefaultInstance(properties, null);

			final Store store = session.getStore("imaps");
			
			final String host = credentialProperties.getProperty("coop.imap.host");
			final String username = credentialProperties.getProperty("coop.imap.username");
			final String password = credentialProperties.getProperty("coop.imap.password");
			
			
			store.connect(host, username, password);

			final Folder inbox = store.getFolder("inbox");
			inbox.open(Folder.READ_ONLY);
			final int messageCount = inbox.getMessageCount();

			final List<Message> messages = Arrays.asList(inbox.getMessages());
//			messages
//				.stream()
//				.filter(
//						message -> message.getSubject().contains(properties.getProperty("coop.email.qualifier")
//				));
			
			inbox.close(true);
			store.close();

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
