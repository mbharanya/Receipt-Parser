package ch.bharanya.receipt_parser.export;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.github.sheigutn.pushbullet.Pushbullet;
import com.github.sheigutn.pushbullet.items.push.sendable.defaults.SendableNotePush;

import ch.bharanya.receipt_parser.Config;
import ch.bharanya.receipt_parser.parser.Receipt;

public class PushBulletExporter implements IExporter {

	private final List<Receipt> receipts;
	Pushbullet pushbullet = new Pushbullet(Config.getInstance().getCredentialProperty("export.pushbullet.apiKey.xmbomb"));

	public PushBulletExporter(final List<Receipt> receipts) {
		this.receipts = receipts;
	}

	@Override
	public void export() throws IOException {
		pushbullet.pushToAllDevices(new SendableNotePush(
			"New receipts parsed!",
			receipts.stream()
				.map( receipt -> {
					return "Date: "+receipt.getDate() +"\n"+
					"Total Price: "+ receipt.getTotalPrice();
				})
				.collect(Collectors.joining("\n\n"))
		));
	}

}
