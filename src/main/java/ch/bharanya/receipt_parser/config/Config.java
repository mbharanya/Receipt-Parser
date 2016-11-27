package ch.bharanya.receipt_parser.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

	private final Properties properties = new Properties();

	private final Properties credentialProperties = new Properties();

	private static Config instance = null;

	private Config() {
		// Exists only to defeat instantiation.
	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
			try {
				instance.loadIfNeeded();
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	private void loadIfNeeded() throws FileNotFoundException, IOException {
		if (properties.isEmpty()) {
			properties.load(new FileInputStream(new File("config.properties")));
		}

		if (credentialProperties.isEmpty()) {
			credentialProperties.load(new FileInputStream(new File("credentials.properties")));
		}
	}

	public String getProperty(final String key) {
		return properties.getProperty(key);
	}

	public String getCredentialProperty(final String key) {
		return credentialProperties.getProperty(key);
	}

	public Properties getProperties() {
		return properties;
	}

}
