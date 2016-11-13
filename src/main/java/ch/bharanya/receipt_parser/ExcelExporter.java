package ch.bharanya.receipt_parser;

import java.io.File;
import java.io.IOException;

import ch.bharanya.receipt_parser.parser.Receipt;

public class ExcelExporter implements IExporter {
	private final File file;

	public ExcelExporter(final File fileToBeModified, final Receipt receipt) {
		this.file = fileToBeModified;
	}

	public void export() throws IOException {
//		final FileInputStream fis = new FileInputStream(file);
//		
//		// Finds the workbook instance for XLSX file
//		final XSSFWorkbook  myWorkBook = new XSSFWorkbook(fis);
//
//		// Return first sheet from the XLSX workbook
//		final XSSFSheet mySheet = myWorkBook.getSheetAt(0);
//
//		// Get iterator to all the rows in current sheet
//		final Iterator<Row> rowIterator = mySheet.iterator();
//
//		// Traversing over each row of XLSX file
//		while (rowIterator.hasNext()) {
//			final Row row = rowIterator.next();
//
//			// For each row, iterate through each columns
//			final Iterator<Cell> cellIterator = row.cellIterator();
//			while (cellIterator.hasNext()) {
//				final Cell cell = cellIterator.next();
//				switch (cell.getCellTypeEnum()) {
////				case Ce:
//					
////					break;
//
//				default:
//					break;
//				}
//				System.out.println(cell.getStringCellValue());
//		
//			}
//		}
//		myWorkBook.close();
	}

}
