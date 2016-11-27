package ch.bharanya.receipt_parser.export.pushbullet;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.items.push.sendable.defaults.SendableNotePush;

import ch.bharanya.receipt_parser.config.Config;
import ch.bharanya.receipt_parser.parser.Receipt;

public class PushBulletUtil {
	
	/**
	* <p>The {@link Logger} for this class.</p>
	*/
	private static final Logger LOG = LoggerFactory.getLogger(PushBulletUtil.class);

	private static Pushbullet pushbullet = new Pushbullet(Config.getInstance().getCredentialProperty("export.pushbullet.apiKey.xmbomb"));

	public static void pushReceipts(final List<Receipt> receipts){
		pushbullet.pushToAllDevices(new SendableNotePush(
				"New receipts parsed!",
				receipts.stream()
					.map( receipt -> {
						return "Date: "+receipt.getDate() +"\n"+
						"Total Price: "+ receipt.getTotalPrice();
					})
					.collect(Collectors.joining("\n\n"))
			));
		LOG.info("Pushed "+receipts.size()+" receipt(s) to pushbullet");
	}
	
	public static void pushError(final Exception e){
		pushbullet.pushToAllDevices(new SendableNotePush(
				"Error parsing receipts: "+e.getMessage(),
				ExceptionUtils.getStackTrace(e)
		));
	}
}
