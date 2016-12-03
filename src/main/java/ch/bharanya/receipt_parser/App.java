package ch.bharanya.receipt_parser;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.bharanya.receipt_parser.config.Config;
import ch.bharanya.receipt_parser.export.ExportingException;
import ch.bharanya.receipt_parser.export.excel.ExcelExporter;
import ch.bharanya.receipt_parser.export.excel.ExporterPool;
import ch.bharanya.receipt_parser.export.pushbullet.PushBulletExporter;
import ch.bharanya.receipt_parser.export.pushbullet.PushBulletUtil;
import ch.bharanya.receipt_parser.parser.Receipt;
import ch.bharanya.receipt_parser.retriever.ProcessedReceiptStoringException;
import ch.bharanya.receipt_parser.retriever.ProcessedReceiptsStore;
import ch.bharanya.receipt_parser.retriever.ReceiptRetrieverPool;
import ch.bharanya.receipt_parser.retriever.RetrievingException;
import ch.bharanya.receipt_parser.retriever.coop.CoopReceiptRetriever;
import ch.bharanya.receipt_parser.retriever.migros.MigrosReceiptRetriever;

public class App {
	/**
	 * <p>
	 * The {@link Logger} for this class.
	 * </p>
	 */
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(final String[] args) {
		final App app = new App();
		app.init();
	}

	private void init() {
		final ReceiptRetrieverPool retrieverPool = new ReceiptRetrieverPool(new CoopReceiptRetriever(), new MigrosReceiptRetriever());
		try{
			final List<Receipt> allRetrievedReceipts = retrieverPool.getAllReceiptsFromAllRetrievers();
			final List<Receipt> newReceipts = allRetrievedReceipts.stream()
					.filter(receipt -> !ProcessedReceiptsStore.getInstance().hasReceiptBeenProcessed(receipt))
					.collect(Collectors.toList());
			
			newReceipts.stream()
				.forEach(newReceipt -> ProcessedReceiptsStore.getInstance().addProcessedReceipt(newReceipt));
			
			LOG.info("Found {} (new) receipt(s)", newReceipts.size());

			if (newReceipts.size() > 0){
				final ExporterPool exporterPool = new ExporterPool(
					new ExcelExporter(new File(Config.getInstance().getProperty("export.excel.file")), newReceipts),
					new PushBulletExporter(newReceipts)
				);
				exporterPool.executeExporters();
			}else{
				LOG.info("Nothing to export!");
			}
			
		}catch (final RetrievingException e){
			LOG.error("Error retrieving receipts",e);
			PushBulletUtil.pushError(e);
		}
		catch (final ExportingException e){
			LOG.error("Error exporting receipts",e);
			PushBulletUtil.pushError(e);
		}
		catch (final ProcessedReceiptStoringException e){
			LOG.error("Error storing receipts in receipt store",e);
			PushBulletUtil.pushError(e);
		}
		catch (final Exception e){
			LOG.error("General error",e);
			PushBulletUtil.pushError(e);
		}
		
		LOG.info("Done!");
	}
}
