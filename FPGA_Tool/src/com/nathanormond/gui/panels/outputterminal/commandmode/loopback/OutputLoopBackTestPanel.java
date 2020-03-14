package com.nathanormond.gui.panels.outputterminal.commandmode.loopback;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import org.jfree.data.general.DefaultValueDataset;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.gui.panels.generic.views.temperatures.TemperaturePanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class OutputLoopBackTestPanel extends AbstractPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private JLabel title;
	public static JTextArea loopBackOutputTextArea;
	private static TemperaturePanel tempPanel;
	private JScrollPane scrollPane;

	public OutputLoopBackTestPanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		title = new JLabel("<html><h2>OUTPUT SNAPSHOT:</h2></html>");
		loopBackOutputTextArea = new JTextArea("listening ...");
		styleOutputArea();
		ObservableFacade.addObserver(ORNames.CORE_TEMPERATURE_DATASET, this);
		tempPanel = new TemperaturePanel(this);
	}
	
	private void styleOutputArea(){ 
		loopBackOutputTextArea.setEditable(false);
		
		Font f = new Font("Arial", Font.BOLD, 12);
		loopBackOutputTextArea.setFont(f);
		
		scrollPane = new JScrollPane(loopBackOutputTextArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(300,350));
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(title, 0, 0);
		addComponentToPanel(scrollPane, 0, 1);
		addComponentToPanel(tempPanel, 1, 1);
	}
	
	@Override
	public void setActionCommands() {
		//not used
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		//not used
	}
	
	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		//not Used
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//not used
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("Live Update :"));
		return this;
	}

	@Override
	public void update(Observable obs, Object obj) {
		float f = (Float) obj;
		
		if(f == -273f) { 
			f = 0;
		}
		
		tempPanel.setDataSetCoreTemp(f);
	}


}
