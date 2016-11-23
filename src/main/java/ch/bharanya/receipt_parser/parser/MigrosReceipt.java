package ch.bharanya.receipt_parser.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class MigrosReceipt extends Receipt
{
	private List<MigrosReceiptElement> receiptElements = new ArrayList<>();

	@Override
	public double getTotalPrice ()
	{
		// TODO: discount?
		return receiptElements.stream().mapToDouble( receipt -> receipt.getPrice() ).sum();
	}

	public String getLocation ()
	{
		return receiptElements.stream().filter( receipt -> StringUtils.isNotBlank( receipt.getStoreName() ) )
				.collect( Collectors.toList() ).get( 0 ).getStoreName();
	}

	/**
	 * @param receiptElements the receiptElements to set
	 */
	public void setReceiptElements ( List<MigrosReceiptElement> receiptElements )
	{
		this.receiptElements = receiptElements;
	}

}
