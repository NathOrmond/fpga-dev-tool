package com.nathanormond.gui.panels.generic.views.temperatures;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.obsrv.observable.ObservableVariable;
import com.nathanormond.obsrv.observer.VariableObserver;

public class MultiTempPanel extends AbstractPanel  {

	private static final long serialVersionUID = 1L;
	private static DefaultValueDataset dataSetLTM, dataSetPEC, dataSetMax, dataSetMin;
	private static ThermometerPlot thermometerPlotLTM, thermometerPlotPEC, thermometerPlotMax, thermometerPlotMin;
	JFreeChart chartLTM, chartPEC, chartMax, chartMin;
    private static final int W = 100;
    private static final int H = 250;
    private static final double UPPER_LIMIT = 100, LOWER_LIMIT = -20; //degrees C    
    
	
	public MultiTempPanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	/****
	 * Initialisation
	 */	
	
	@Override
	public void instantiateComponents() {
		
		dataSetLTM = new DefaultValueDataset(ObservableFacade.getValue(ORNames.LTM_TEMP_DATASET));
		dataSetPEC = new DefaultValueDataset(ObservableFacade.getValue(ORNames.PEC_TEMP_DATASET));
		dataSetMax = new DefaultValueDataset(ObservableFacade.getValue(ORNames.MAX_TEMP_DATASET));
		dataSetMin = new DefaultValueDataset(ObservableFacade.getValue(ORNames.MIN_TEMP_DATASET));
		
		thermometerPlotLTM = new ThermometerPlot(dataSetLTM);
		thermometerPlotLTM.setRange(LOWER_LIMIT, UPPER_LIMIT);
		thermometerPlotLTM.setUnits(ThermometerPlot.UNITS_CELCIUS);
		chartLTM = new JFreeChart("LTM", JFreeChart.DEFAULT_TITLE_FONT, thermometerPlotLTM, true);
		
		thermometerPlotPEC = new ThermometerPlot(dataSetPEC);
		thermometerPlotPEC.setRange(LOWER_LIMIT, UPPER_LIMIT);
		thermometerPlotPEC.setSubrangePaint(0, Color.green.darker());
		thermometerPlotPEC.setSubrangePaint(1, Color.orange);
		thermometerPlotPEC.setSubrangePaint(2, Color.red.darker());
		thermometerPlotPEC.setUnits(ThermometerPlot.UNITS_CELCIUS);
		chartPEC = new JFreeChart("PEC", JFreeChart.DEFAULT_TITLE_FONT, thermometerPlotPEC, true);
		
		thermometerPlotMax = new ThermometerPlot(dataSetMax);
		thermometerPlotMax.setRange(LOWER_LIMIT, UPPER_LIMIT);
		thermometerPlotMax.setSubrangePaint(0, Color.green.darker());
		thermometerPlotMax.setSubrangePaint(1, Color.orange);
		thermometerPlotMax.setSubrangePaint(2, Color.red.darker());
		thermometerPlotMax.setUnits(ThermometerPlot.UNITS_CELCIUS);
		chartMax = new JFreeChart("MAX", JFreeChart.DEFAULT_TITLE_FONT, thermometerPlotMax, true);
		
		thermometerPlotMin = new ThermometerPlot(dataSetMin);
		thermometerPlotMin.setRange(LOWER_LIMIT, UPPER_LIMIT);
		thermometerPlotMin.setSubrangePaint(0, Color.green.darker());
		thermometerPlotMin.setSubrangePaint(1, Color.orange);
		thermometerPlotMin.setSubrangePaint(2, Color.red.darker());
		thermometerPlotMin.setUnits(ThermometerPlot.UNITS_CELCIUS);
		chartMin = new JFreeChart("MIN", JFreeChart.DEFAULT_TITLE_FONT, thermometerPlotMin, true);
		
	}
	
	
	@Override
	public void setActionCommands() {
		//not used
	}
	
	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(new ChartPanel(chartLTM, W, H, W, H, W, H, false, true, true, true, true, true), 0, 0);
		addComponentToPanel(new ChartPanel(chartPEC, W, H, W, H, W, H, false, true, true, true, true, true), 1, 0);
		addComponentToPanel(new ChartPanel(chartMax, W, H, W, H, W, H, false, true, true, true, true, true), 0, 1);
		addComponentToPanel(new ChartPanel(chartMin, W, H, W, H, W, H, false, true, true, true, true, true), 1, 1);
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
	

	@Override
	public JPanel getPanel() {
		return this;
	}
	

	public static void setDataSetLTM(Float f) {
		MultiTempPanel.dataSetLTM.setValue(f);
	}
	
	public static void setDataSetPEC(Float f) {
		MultiTempPanel.dataSetPEC.setValue(f);
	}
	
	public static void setDataSetMax(Float f) {
		MultiTempPanel.dataSetMax.setValue(f);
	}
	
	public static void setDataSetMin(Float f) {
		MultiTempPanel.dataSetMin.setValue(f);
	}
}
