package ch.bharanya.receipt_parser.parser;

public class CoopReceipt extends Receipt {
	
	private final EStore store = EStore.COOP;

	public EStore getStore() {
		return store;
	}


}
