package com.nathanormond.gui.panels.generic.views.temperatures;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class TemperaturePanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private static DefaultValueDataset dataSetCoreTemp;
	public static ThermometerPlot thermometerPlot;
	JFreeChart chart;
    private static final int W = 110;
    private static final int H = 3 * W;
    private static final double UPPER_LIMIT = 100, LOWER_LIMIT = -20; //degrees C
	
	public TemperaturePanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	/****
	 * Initialisation
	 */
	
	@Override
	public void instantiateComponents() {
		dataSetCoreTemp = new DefaultValueDataset(ObservableFacade.getValue(ORNames.CORE_TEMPERATURE_DATASET));
		
		thermometerPlot = new ThermometerPlot(dataSetCoreTemp);
		styling();
		chart = new JFreeChart("CORE", JFreeChart.DEFAULT_TITLE_FONT, thermometerPlot, true);
	}
	
	private void styling() { 
		thermometerPlot.setRange(LOWER_LIMIT, UPPER_LIMIT);
		
		thermometerPlot.setSubrangePaint(0, Color.green.darker());
		thermometerPlot.setSubrangePaint(1, Color.orange);
		thermometerPlot.setSubrangePaint(2, Color.red.darker());
		
		thermometerPlot.setUnits(ThermometerPlot.UNITS_CELCIUS);
	}
	
	@Override
	public void setActionCommands() {
		//not used
	}
	
	@Override
	public void addComponentsToPanel() {
		this.add(new ChartPanel(chart, W, H, W, H, W, H,false, true, true, true, true, true));
	}
	
	/****
	 * Action Listeners
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		//not used
	}

	@Override
	public void addLocalActionListeners() {
		//not used
	}
	
	/****
	 *	Actions
	 */
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//not used
	}
	
	/**
	 * Class Methods 
	 */
	
	public static void updateValues() { 
		
		
	}

	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("Thermometer"));
		return this;
	}
	
	public static void setDataSetCoreTemp(float f) {
		TemperaturePanel.dataSetCoreTemp.setValue(f);
	}

}
