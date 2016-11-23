package ch.bharanya.receipt_parser.parser;

import java.util.Date;

public class MigrosReceiptElement
{
	private Date purchaseDate;
	private String storeName;
	private long transactionNumber;
	private String articleName;
	private double amount;
	private double discount;
	private double price;
	/**
	 * @return the purchaseDate
	 */
	public Date getPurchaseDate ()
	{
		return purchaseDate;
	}
	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate ( Date purchaseDate )
	{
		this.purchaseDate = purchaseDate;
	}
	/**
	 * @return the storeName
	 */
	public String getStoreName ()
	{
		return storeName;
	}
	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName ( String storeName )
	{
		this.storeName = storeName;
	}
	/**
	 * @return the transactionNumber
	 */
	public long getTransactionNumber ()
	{
		return transactionNumber;
	}
	/**
	 * @param transactionNumber the transactionNumber to set
	 */
	public void setTransactionNumber ( long transactionNumber )
	{
		this.transactionNumber = transactionNumber;
	}
	/**
	 * @return the articleName
	 */
	public String getArticleName ()
	{
		return articleName;
	}
	/**
	 * @param articleName the articleName to set
	 */
	public void setArticleName ( String articleName )
	{
		this.articleName = articleName;
	}
	/**
	 * @return the amount
	 */
	public double getAmount ()
	{
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount ( double amount )
	{
		this.amount = amount;
	}
	/**
	 * @return the discount
	 */
	public double getDiscount ()
	{
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount ( double discount )
	{
		this.discount = discount;
	}
	/**
	 * @return the price
	 */
	public double getPrice ()
	{
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice ( double price )
	{
		this.price = price;
	}
}
