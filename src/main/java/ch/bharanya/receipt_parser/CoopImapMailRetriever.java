package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.lang3.StringUtils;

public class CoopImapMailRetriever {

	public List<File> getMails() {
		List<File> attachments = new ArrayList<>();
		try {
			final Session session = Session.getDefaultInstance(Config.getInstance().getProperties(), null);

			final Store store = session.getStore("imaps");

			final String host = Config.getInstance().getCredentialProperty("coop.mail.imap.host");
			final String username = Config.getInstance().getCredentialProperty("coop.mail.imap.username");
			final String password = Config.getInstance().getCredentialProperty("coop.mail.imap.password");

			store.connect(host, username, password);

			final Folder inbox = store.getFolder("inbox");
			inbox.open(Folder.READ_ONLY);
			final List<Message> messages = Arrays.asList(inbox.getMessages());

			final List<Message> validMessages = messages.stream().filter(message -> {
				try {
					return message.getSubject().contains(Config.getInstance().getProperty("coop.email.subjectQualifier"));
				} catch (final MessagingException msge) {
					msge.printStackTrace();
				}
				return false;
			}).collect(Collectors.toList());

			attachments = getAttachments(validMessages);
			inbox.close(true);
			store.close();

		} catch (final Exception e) {
			e.printStackTrace();
		}
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
