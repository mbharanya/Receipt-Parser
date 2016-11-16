package ch.bharanya.receipt_parser.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.bharanya.receipt_parser.parser.Receipt;

public class ExcelExporter implements IExporter {
	/**
	 * <p>The logger for this class.</p>
	 */
	private static Logger LOG = LoggerFactory.getLogger( ExcelExporter.class );

	private static final int TOTAL_PRICE_COLUMN_INDEX = 2;
	private static final int FIRST_DATA_ROW_NUMBER_OFFSET = 0;
	private final File file;
	private List<Receipt> receipts = new ArrayList<>();

	public ExcelExporter(final File fileToBeModified, final List<Receipt> receipts) {
		this.file = fileToBeModified;
		this.receipts = receipts;
	}
	
	public ExcelExporter(final File fileToBeModified, final Receipt receipt) {
		this.file = fileToBeModified;
		receipts.add(receipt);
	}


	@Override
	public void export() throws IOException {
		if (receipts.size() < 1){
			return;
		}
		
		final FileInputStream fis = new FileInputStream(file);

		// Finds the workbook instance for XLSX file
		final XSSFWorkbook workbook = new XSSFWorkbook(fis);

		for (Receipt receipt : receipts){
			String sheetName = ExcelUtil.getSheetName(receipt);
			XSSFSheet currentMonthWorkSheet = workbook.getSheet(sheetName);
			if (currentMonthWorkSheet == null){
				LOG.info( "Sheet {} was created", sheetName );
				currentMonthWorkSheet = workbook.createSheet( sheetName );
			}
			
			Row row = currentMonthWorkSheet.getRow( FIRST_DATA_ROW_NUMBER_OFFSET + receipt.getDate().getDate());
			Cell cell = row.createCell( TOTAL_PRICE_COLUMN_INDEX );
			cell.setCellValue( receipt.getTotalPrice() );
			LOG.info( "Setting totalprice {} in sheet {} of file {}", receipt.getTotalPrice(), currentMonthWorkSheet.getSheetName(), file.getName());
		}

        // open an OutputStream to save written data into XLSX file
        FileOutputStream os = new FileOutputStream(file);
        workbook.write(os);
        
        LOG.info( "Writing to file {}", file.getName() );
        
		workbook.close();
	}



}
