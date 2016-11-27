package ch.bharanya.receipt_parser.parser.migros;

import ch.bharanya.receipt_parser.config.Config;

public enum EMigrosOverviewCsvHeader {
	DATE(Config.getInstance().getProperty("migros.parser.csv.overview.dateCol")),
	TIME(Config.getInstance().getProperty("migros.parser.csv.overview.timeCol")),
	LOCATION(Config.getInstance().getProperty("migros.parser.csv.overview.locationCol")),
	CHECKOUT_NUMBER(Config.getInstance().getProperty("migros.parser.csv.overview.checkOutNumberCol")),
	TRANSACTION_NUMBER(Config.getInstance().getProperty("migros.parser.csv.overview.transactionNumberCol")),
	TOTAL_PRICE(Config.getInstance().getProperty("migros.parser.csv.overview.totalPriceCol")),
	CUMULUS_POINTS(Config.getInstance().getProperty("migros.parser.csv.overview.cumulusPointsCol"));
	
	private String colName;

	EMigrosOverviewCsvHeader(final String colName){
		this.colName = colName;
	}
	
}
