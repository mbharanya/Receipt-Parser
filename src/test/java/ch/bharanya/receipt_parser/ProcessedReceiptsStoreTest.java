package ch.bharanya.receipt_parser;

import org.junit.Test;

import junit.framework.Assert;

public class ProcessedReceiptsStoreTest {
	TestReceipt receipt0 = new TestReceipt();
	TestReceipt receipt1 = (TestReceipt) new TestReceipt().setId("new id");
	
	@Test
	public void testAddProcessedReceipt(){
		final ProcessedReceiptsStore receiptsStore = ProcessedReceiptsStore.getInstance();
		receiptsStore.addProcessedReceipt(receipt0);
		Assert.assertTrue(receiptsStore.hasReceiptBeenProcessed(receipt0));
		receiptsStore.addProcessedReceipt(receipt1);
		Assert.assertTrue(receiptsStore.hasReceiptBeenProcessed(receipt1));
	}
	
	@Test
	public void testAddSameReceiptTwice(){
		final ProcessedReceiptsStore receiptsStore = ProcessedReceiptsStore.getInstance();
		receiptsStore.addProcessedReceipt(receipt0);
		receiptsStore.addProcessedReceipt(receipt0);
		Assert.assertTrue(receiptsStore.hasReceiptBeenProcessed(receipt0));
	}
}
