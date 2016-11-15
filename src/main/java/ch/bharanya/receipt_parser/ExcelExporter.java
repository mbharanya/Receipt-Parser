package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ch.bharanya.receipt_parser.parser.Receipt;

public class ExcelExporter implements IExporter {
	private final File file;
	private final Receipt receipt;

	public ExcelExporter(final File fileToBeModified, final Receipt receipt) {
		this.file = fileToBeModified;
		this.receipt = receipt;
	}

	@Override
	public void export() throws IOException {
		final FileInputStream fis = new FileInputStream(file);

		// Finds the workbook instance for XLSX file
		final XSSFWorkbook workbook = new XSSFWorkbook(fis);

		final XSSFSheet currentMonthWorkSheet = workbook.getSheet(ExcelUtil.getSheetName(receipt));		

		workbook.close();
	}



}
