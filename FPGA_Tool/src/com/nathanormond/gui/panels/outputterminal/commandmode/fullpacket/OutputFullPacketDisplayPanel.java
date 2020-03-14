package com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.hotlink.HotLinkInfoPanel;
import com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.packet.PacketInfoPanel;
import com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.temperatures.TemperaturesInfoPanel;
import com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.voltages.VoltagesInfoPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class OutputFullPacketDisplayPanel extends AbstractPanel implements Observer {

	private static final long serialVersionUID = 1L;
	public static JTextArea loopBackOutputTextArea = null;
	/*************************************
	 * INITIALISATION
	 */
	
	private JLabel updateCount;
	public static JTextArea counter;
	
	
	private JTabbedPane tabbedPane;
	private PacketInfoPanel packetInfoPanel;
	private HotLinkInfoPanel hotLinkInfoPanel;
	private TemperaturesInfoPanel temperaturesInfoPanel;
	private VoltagesInfoPanel voltagesInfoPanel;
	
	public OutputFullPacketDisplayPanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	@Override
	public void instantiateComponents() {
		updateCount = new JLabel("Count");
		counter = new JTextArea();
		
		tabbedPane = new JTabbedPane();
		
		packetInfoPanel = new PacketInfoPanel(this);
		hotLinkInfoPanel = new HotLinkInfoPanel(this);
		temperaturesInfoPanel = new TemperaturesInfoPanel(this);
		voltagesInfoPanel = new VoltagesInfoPanel(this);
		
		tabbedPane.add("Packet Info", packetInfoPanel);
		tabbedPane.add("Hot Link Info", hotLinkInfoPanel);
		tabbedPane.add("Temperatures", temperaturesInfoPanel);
		tabbedPane.add("Voltages", voltagesInfoPanel);
		
		ObservableFacade.addObserver(ORNames.PEC_TEMP_DATASET, this);
		ObservableFacade.addObserver(ORNames.LTM_TEMP_DATASET, this);
		ObservableFacade.addObserver(ORNames.MAX_TEMP_DATASET, this);
		ObservableFacade.addObserver(ORNames.MIN_TEMP_DATASET, this);
		ObservableFacade.addObserver(ORNames.CORE_TEMPERATURE_DATASET, this);
		
		componentStyling();
	}
	
	private void componentStyling() { 
		Font f = new Font("Arial", Font.BOLD, 15);
		
		f = new Font("Arial", Font.BOLD, 12);
		updateCount.setFont(f);
		counter.setFont(f);
		counter.setFont(f);
		counter.setPreferredSize(new Dimension(75,20));
		counter.setEditable(false);
		counter.setText("0");
	}

	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		//not used
	}

	@Override
	public void addLocalActionListeners() {
		//not used
	}

	@Override
	public void setActionCommands() {
		//not used
	}
	

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(updateCount, 0, 0);
		addComponentToPanel(counter, 0, 1);
		
		addComponentToPanel(tabbedPane, 0, 2);
	}
	
	/*************************************
	 * ACTIONS
	 */
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/*************************************
	 * GETTERS & SETTERS
	 */
	
	@Override
	public JPanel getPanel() {
		return this;
	}

	@Override
	public void update(Observable obs, Object obj) {

		float f = (Float) obj;
		
		if(f == -273f) { 
			f = 0f;
		}
		
		switch(obs.toString()) { 
			case ORNames.CORE_TEMPERATURE_DATASET:
				temperaturesInfoPanel.mainTempPanel.setDataSetCoreTemp(f);
			break;
			
			case ORNames.MAX_TEMP_DATASET:
				temperaturesInfoPanel.multiTempPanel.setDataSetMax(f);
				break;
				
			case ORNames.MIN_TEMP_DATASET:
				temperaturesInfoPanel.multiTempPanel.setDataSetMin(f);
				break;
				
			case ORNames.PEC_TEMP_DATASET:
				temperaturesInfoPanel.multiTempPanel.setDataSetPEC(f);
				break;
				
			case ORNames.LTM_TEMP_DATASET:
				temperaturesInfoPanel.multiTempPanel.setDataSetLTM(f);
				break;	
		}
	}

	

}
