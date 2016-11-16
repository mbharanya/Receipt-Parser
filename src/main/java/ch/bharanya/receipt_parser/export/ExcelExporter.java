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

import ch.bharanya.receipt_parser.parser.Receipt;

public class ExcelExporter implements IExporter {
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
		final FileInputStream fis = new FileInputStream(file);

		// Finds the workbook instance for XLSX file
		final XSSFWorkbook workbook = new XSSFWorkbook(fis);

		for (Receipt receipt : receipts){
			final XSSFSheet currentMonthWorkSheet = workbook.getSheet(ExcelUtil.getSheetName(receipt));
			Row row = currentMonthWorkSheet.getRow( FIRST_DATA_ROW_NUMBER_OFFSET + receipt.getDate().getDate());
			Cell cell = row.createCell( TOTAL_PRICE_COLUMN_INDEX );
			cell.setCellValue( receipt.getTotalPrice() );
		}

        // open an OutputStream to save written data into XLSX file
        FileOutputStream os = new FileOutputStream(file);
        workbook.write(os);

		workbook.close();
	}



}
