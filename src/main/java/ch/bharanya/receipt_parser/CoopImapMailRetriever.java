package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoopImapMailRetriever {
	/**
	 * <p>
	 * The {@link Logger} for this class.
	 * </p>
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CoopImapMailRetriever.class);

	private static final String INBOX_FOLDER_NAME = "inbox";
	private static final String MAIL_STORE_TYPE = "imaps";

	public List<File> getPdfReceipts() throws MessagingException, IOException {
		List<File> attachments = new ArrayList<>();
		final Session session = Session.getDefaultInstance(Config.getInstance().getProperties(), null);

		final Store store = session.getStore(MAIL_STORE_TYPE);

		final String host = Config.getInstance().getCredentialProperty("coop.mail.imap.host");
		final String username = Config.getInstance().getCredentialProperty("coop.mail.imap.username");
		final String password = Config.getInstance().getCredentialProperty("coop.mail.imap.password");

		LOG.info("Connecting to host {} with user {}", host, username);
		store.connect(host, username, password);

		final Folder inbox = store.getFolder(INBOX_FOLDER_NAME);
		inbox.open(Folder.READ_ONLY);
		final List<Message> messages = Arrays.asList(inbox.getMessages());

		final List<Message> validMessages = new ArrayList<>();

		final Date yesterday = new Date();
		yesterday.setDate(new Date().getDate() - 2);
		yesterday.setHours(0);
		yesterday.setMinutes(0);
		yesterday.setSeconds(0);
		for (final Message message : messages) {
			if (message != null && message.getReceivedDate().after(yesterday)) {
				final String messageSubject = message.getSubject();
				// TODO: StringUtils
				if (messageSubject != null && messageSubject.contains(Config.getInstance().getProperty("coop.email.subjectQualifier"))) {
					validMessages.add(message);
				}
			}
		}

		LOG.info("Found {} qualifying messages containing {}", validMessages.size(), Config.getInstance().getProperty("coop.email.subjectQualifier"));

		attachments = getAttachments(validMessages);
		
		inbox.close(true);
		store.close();

		return attachments;
	}

	private List<File> getAttachments(final List<Message> messages) throws IOException, MessagingException {
		final List<File> attachments = new ArrayList<File>();
		for (final Message message : messages) {
			final Multipart multipart = (Multipart) message.getContent();
			for (int i = 0; i < multipart.getCount(); i++) {
				final BodyPart bodyPart = multipart.getBodyPart(i);
				if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) && !StringUtils.isNotBlank(bodyPart.getFileName())) {
					continue; // dealing with attachments only
				}
				final InputStream is = bodyPart.getInputStream();
				final File f = new File(Config.getInstance().getProperty("coop.email.attachmentDir") + File.separator + bodyPart.getFileName());
				final FileOutputStream fos = new FileOutputStream(f);
				final byte[] buf = new byte[4096];
				int bytesRead;
				while ((bytesRead = is.read(buf)) != -1) {
					fos.write(buf, 0, bytesRead);
				}
				fos.close();
				attachments.add(f);
			}

		}
		return attachments;
	}

}
