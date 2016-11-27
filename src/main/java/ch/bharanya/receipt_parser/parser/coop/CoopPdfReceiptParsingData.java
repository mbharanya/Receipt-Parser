package ch.bharanya.receipt_parser.parser.coop;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CoopPdfReceiptParsingData
{

	/**
	 * <p>
	 * The {@link Logger} for this class.
	 * </p>
	 */
	private static final Logger LOG = LoggerFactory.getLogger( CoopPdfReceiptParsingData.class );
	File file;
	PDDocument document;
	String text;

	public CoopPdfReceiptParsingData ( File file )
	{
		this.file = file;
	
	}

	/**
	 * @return the file
	 */
	public File getFile ()
	{
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile ( File file )
	{
		this.file = file;
	}

	/**
	 * @return the document
	 */
	public PDDocument getDocument ()
	{
		return document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument ( PDDocument document )
	{
		this.document = document;
	}

	/**
	 * @return the text
	 */
	public String getText ()
	{
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText ( String text )
	{
		this.text = text;
	}

}
