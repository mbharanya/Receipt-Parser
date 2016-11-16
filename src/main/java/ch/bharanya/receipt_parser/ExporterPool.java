package ch.bharanya.receipt_parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.bharanya.receipt_parser.export.IExporter;

public class ExporterPool
{
	private List<IExporter> exporters = new ArrayList<>();
	
	public ExporterPool (IExporter... exporters)
	{
		this.exporters.addAll( Arrays.asList( exporters ));
	}
	
	public void executeExporters() throws IOException{
		for ( IExporter exporter : exporters )
		{
			exporter.export();
		}
	}

	public void addExporter ( IExporter exporter )
	{
		exporters.add(exporter);
	}
}
