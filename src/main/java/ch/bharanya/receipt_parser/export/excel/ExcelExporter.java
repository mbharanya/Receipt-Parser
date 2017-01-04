package ch.bharanya.receipt_parser.export.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.bharanya.receipt_parser.config.Config;
import ch.bharanya.receipt_parser.export.IExporter;
import ch.bharanya.receipt_parser.parser.Receipt;

public class ExcelExporter implements IExporter {
	/**
	 * <p>
	 * The logger for this class.
	 * </p>
	 */
	private static Logger LOG = LoggerFactory.getLogger(ExcelExporter.class);

	private static final int TOTAL_PRICE_COLUMN_INDEX = Integer.valueOf(Config.getInstance().getProperty("export.excel.totalprice.columnIndex"));
	private static final int FIRST_DATA_ROW_NUMBER_OFFSET = Integer.valueOf(Config.getInstance().getProperty("export.excel.firstDataRowNumberOffset"));;
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
		if (receipts.size() < 1) {
			return;
		}

		final FileInputStream fis = new FileInputStream(file);

		// Finds the workbook instance for XLSX file
		final XSSFWorkbook workbook = new XSSFWorkbook(fis);

		for (final Receipt receipt : receipts) {
			final String sheetName = ExcelUtil.getSheetName(receipt);
			XSSFSheet currentMonthWorkSheet = workbook.getSheet(sheetName);
			if (currentMonthWorkSheet == null) {
				LOG.info("Sheet {} was created", sheetName);
				currentMonthWorkSheet = workbook.createSheet(sheetName);
			}

			final int rownum = FIRST_DATA_ROW_NUMBER_OFFSET + receipt.getDate().getDate();
			Row row = currentMonthWorkSheet.getRow(rownum);
			if (row == null){
				row = currentMonthWorkSheet.createRow(rownum);
			}

			updateOrCreateCellValue(row, TOTAL_PRICE_COLUMN_INDEX, receipt.getTotalPrice());

			LOG.info("Adding totalprice [{}] in sheet [{}] of file [{}]", receipt.getTotalPrice(), currentMonthWorkSheet.getSheetName(), file.getName());
		}

		// open an OutputStream to save written data into XLSX file
		final FileOutputStream os = new FileOutputStream(file);
		workbook.write(os);
		XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

		LOG.info("Writing to file {}", file.getName());

		workbook.close();
	}

	private void updateOrCreateCellValue(final Row row, final int colIndex, final double cellValue) {
		Cell cell = row.getCell(colIndex);
		if (cell == null) {
			cell = row.createCell(colIndex);
		}

		switch (cell.getCellTypeEnum()) {
		case FORMULA:
			final String existingCellFormula = cell.getCellFormula();
			cell.setCellFormula(extendCellFormula(existingCellFormula, cellValue));
			break;
		case NUMERIC:
			final double doubleCellValue = cell.getNumericCellValue();
			cell.setCellFormula(createCellFormula(doubleCellValue, cellValue));
			break;
		case BLANK:
			LOG.debug("Setting new cell value {}", cellValue);
			cell.setCellValue(cellValue);
			break;
		default:
			break;
		}
	}

	private String extendCellFormula(final String currentFormula, final double newValue) {
		LOG.info("Adding {} to existing formula {} - new value: \"{}\"", newValue, currentFormula, currentFormula + "+" + newValue);
		return currentFormula + "+" + newValue;
	}

	private String createCellFormula(final double currentValue, final double newValue) {
		LOG.info("Adding {} to existing value {} - new value: \"{}\"", newValue, currentValue, currentValue + "+" + newValue);
		return currentValue + "+" + newValue;
	}

}
