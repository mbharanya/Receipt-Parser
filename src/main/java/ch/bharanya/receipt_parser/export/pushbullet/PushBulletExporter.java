package ch.bharanya.receipt_parser.export.pushbullet;

import java.io.IOException;
import java.util.List;

import ch.bharanya.receipt_parser.export.IExporter;
import ch.bharanya.receipt_parser.parser.Receipt;

public class PushBulletExporter implements IExporter {

	private final List<Receipt> receipts;

	public PushBulletExporter(final List<Receipt> receipts) {
		this.receipts = receipts;
	}

	@Override
	public void export() throws IOException {
		PushBulletUtil.pushReceipts(receipts);
	}

}
