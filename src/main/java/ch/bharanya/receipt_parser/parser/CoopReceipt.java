package ch.bharanya.receipt_parser.parser;

import java.util.Date;

public class CoopReceipt extends Receipt {
	private final EStore store = EStore.COOP;
	private String location;


	public CoopReceipt(final String id, final Date date, final double totalPrice) {
		super(id, date, totalPrice);
	}
	
	
	public CoopReceipt(final String id, final Date date, final double totalPrice, final String location) {
		super(id, date, totalPrice);
		setLocation( location);
	}



	public EStore getStore() {
		return store;
	}


	/**
	 * @return the location
	 */
	public String getLocation ()
	{
		return location;
	}
	

	/**
	 * @param location the location to set
	 */
	public void setLocation ( final String location )
	{
		this.location = location;
	}


}
