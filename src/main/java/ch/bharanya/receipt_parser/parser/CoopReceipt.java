package ch.bharanya.receipt_parser.parser;

import java.util.Date;

public class CoopReceipt extends Receipt {
	
	public CoopReceipt(final Date date, final double totalPrice) {
		super(date, totalPrice);
	}

	private final EStore store = EStore.COOP;

	public EStore getStore() {
		return store;
	}


}
