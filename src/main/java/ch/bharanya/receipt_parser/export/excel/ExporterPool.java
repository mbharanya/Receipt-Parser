package ch.bharanya.receipt_parser.export.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.bharanya.receipt_parser.export.ExportingException;
import ch.bharanya.receipt_parser.export.IExporter;

public class ExporterPool
{
	private final List<IExporter> exporters = new ArrayList<>();
	
	public ExporterPool (final IExporter... exporters)
	{
		this.exporters.addAll( Arrays.asList( exporters ));
	}
	
	public void executeExporters() throws ExportingException{
		for ( final IExporter exporter : exporters )
		{
			try {
				exporter.export();
			} catch (final IOException e) {
				throw new ExportingException(e);
			}
		}
	}

	public void addExporter ( final IExporter exporter )
	{
		exporters.add(exporter);
	}
}
