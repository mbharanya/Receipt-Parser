package ch.bharanya.receipt_parser.parser;

import java.util.Date;

public class Receipt {
	private String id;
	private Date date;
	private double totalPrice;
	private final EStore store = EStore.UNKNOWN;

	public Receipt() {
	}
	
	public Receipt(final String id, final Date date, final double totalPrice) {
		this.id = id;
		this.date = date;
		this.totalPrice = totalPrice;
	}

	public Date getDate() {
		return date;
	}

	public Receipt setDate(final Date date) {
		this.date = date;
		return null;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public Receipt setTotalPrice(final double totalPrice) {
		this.totalPrice = totalPrice;
		return null;
	}

	public String getId() {
		return id;
	}

	public Receipt setId(final String id) {
		this.id = id;
		return this;
	}
	
	@Override
	public boolean equals(final Object obj) {
		final Receipt otherReceipt = (Receipt) obj;
		return otherReceipt.getId().equals(getId());
	}

	public EStore getStore() {
		return store;
	}
}
