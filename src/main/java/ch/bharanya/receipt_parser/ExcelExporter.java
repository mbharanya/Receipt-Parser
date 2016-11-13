package ch.bharanya.receipt_parser;

import java.io.File;

public class ExcelExporter implements IExporter {
	private final File file;

	public ExcelExporter(final File fileToBeModified) {
		this.file = fileToBeModified;
	}

	public void export() {
//		final FileInputStream fis = new FileInputStream(myFile);
//
//		// Finds the workbook instance for XLSX file
//		final XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
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
//
//				final Cell cell = cellIterator.next();
//
//				switch (cell.getCellType()) {
//				case CellType.STRING:
//					System.out.print(cell.getStringCellValue() + "\t");
//					break;
//				case Cell.CELL_TYPE_NUMERIC:
//					System.out.print(cell.getNumericCellValue() + "\t");
//					break;
//				case Cell.CELL_TYPE_BOOLEAN:
//					System.out.print(cell.getBooleanCellValue() + "\t");
//					break;
//				default:
//
//				}
//			}
//			System.out.println("");
//		}
	}

}
