package com.nathanormond.gui.panels.generic.views.voltages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class VoltagePanel extends AbstractPanel implements Observer {

	public static DialPlot plot;
	public static DefaultValueDataset dataSetVoltage;
	private static final long serialVersionUID = 1L;

	public VoltagePanel(ActionListener publicListener) {
		super(publicListener);
	}

	/****
	 * Initialisation
	 */

	@Override
	public void instantiateComponents() {
		dataSetVoltage = new DefaultValueDataset(ObservableFacade.getValue(ORNames.CORE_VOLTAGE_DATASET));
		ObservableFacade.addObserver(ORNames.CORE_VOLTAGE_DATASET, this);
	}

	private ChartPanel buildDialPlot(int minimumValue, int maximumValue, int majorTickGap, String title) {

		plot = new DialPlot(dataSetVoltage);
		plot.setDialFrame(new StandardDialFrame());
		plot.addLayer(new DialValueIndicator(0));
		plot.addLayer(new DialPointer.Pointer());
		

		int redLine = 2;
		plot.addLayer(new StandardDialRange(minimumValue, redLine, Color.green));
		plot.addLayer(new StandardDialRange(redLine, maximumValue, Color.red));

		StandardDialScale scale = new StandardDialScale(minimumValue, maximumValue, -120, -300, majorTickGap, majorTickGap - 1);
		scale.setTickRadius(0.88);
		scale.setTickLabelOffset(0.20);
		plot.addScale(0, scale);

		return new ChartPanel(new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,plot, true)) {
			private static final long serialVersionUID = 1L;

			@Override
		    public Dimension getPreferredSize() {
		        return new Dimension(350, 350);
		    }
		};
	}


	@Override
	public void setActionCommands() {
		// not used
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(buildDialPlot(0, 4, 1, "CORE VOLTAGE"), 0, 0);
	}

	/****
	 * Action Listeners
	 */

	@Override
	public void addPublicActionListeners(ActionListener listener) {
		// not used
	}

	@Override
	public void addLocalActionListeners() {
		// not used
	}

	/****
	 * Actions
	 */

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// not used
	}

	/**
	 * Class Methods
	 */

	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("VOLTAGE"));
		return this;
	}

	@Override
	public void update(Observable obs, Object obj) {
		dataSetVoltage.setValue((Number)obj);
		
	}

}
