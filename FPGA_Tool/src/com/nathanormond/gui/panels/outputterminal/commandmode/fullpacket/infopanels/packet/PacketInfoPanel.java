package com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.packet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class PacketInfoPanel extends AbstractPanel {
	

	private static final long serialVersionUID = 1L;
	private JLabel packetHeader;
	public static JTextArea packetHeaderArea;
	
	private JLabel ther;
	public static JTextArea therArea;

	
	private JLabel fullOutputDump;
	public static JTextArea fullOutputDumpArea;
	private JScrollPane scrollPane;
	
	
	public PacketInfoPanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	/*************************************
	 * INITIALISATION
	 */
	
	
	@Override
	public void instantiateComponents() {
		this.setPreferredSize(new Dimension(ObservableFacade.getValue(ORNames.PREFERRED_OUTPUT_PANEL_WIDTH), ObservableFacade.getValue(ORNames.PREFERRED_OUTPUT_PANEL_HEIGHT)));
		
		packetHeader = new JLabel("Header");
		packetHeaderArea = new JTextArea();
		
		ther = new JLabel("THER");
		therArea = new JTextArea();
		
		fullOutputDump = new JLabel("Output Dump");
		fullOutputDumpArea = new JTextArea();
		scrollPane = new JScrollPane(fullOutputDumpArea);
		
		
		styleComponents();
	}
	
	private void styleComponents() { 
		Font f = new Font("Arial", Font.BOLD, 15);
		packetHeader.setFont(f);
		ther.setFont(f);
		fullOutputDump.setFont(f);
		
		f = new Font("Arial", Font.BOLD, 12);
		styleArea(f, packetHeaderArea);
		styleArea(f, therArea);
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(350,350));
	}
	
	private void styleArea(Font f, JTextArea area) { 
		area.setFont(f);
		area.setFont(f);
		area.setPreferredSize(new Dimension(200,20));
		area.setEditable(false);
	}

	@Override
	public void addComponentsToPanel() {
		gbc.anchor = GridBagConstraints.WEST;
		addComponentToPanel(packetHeader, 0, 0);
		addComponentToPanel(packetHeaderArea, 1, 0);
		addComponentToPanel(ther, 0, 1);
		addComponentToPanel(therArea, 1, 1);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		addComponentToPanel(fullOutputDump, 0, 2);
		addComponentToPanel(scrollPane, 1, 2);
	}
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		//not used
	}

	@Override
	public void addLocalActionListeners() {
		// not used
	}

	@Override
	public void setActionCommands() {
		// not used
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


}
