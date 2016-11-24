package ch.bharanya.receipt_parser.parser;

public enum EMigrosOverviewCsvHeader {
	DATE("Datum"),
	TIME("Zeit"),
	LOCATION("Filiale"),
	CHECKOUT_NUMBER("Kassennummer"),
	TRANSACTION_NUMBER("Transaktionsnummer"),
	TOTAL_PRICE("Einkaufsbetrag"),
	CUMULUS_POINTS("Bonuspunkte");
	
	private String colName;

	EMigrosOverviewCsvHeader(final String colName){
		this.colName = colName;
	}
	
}
