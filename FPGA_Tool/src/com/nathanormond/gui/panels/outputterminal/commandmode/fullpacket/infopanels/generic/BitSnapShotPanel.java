package com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.generic;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.nathanormond.bitmanipulations.BitManipulations;
import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;

public class BitSnapShotPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	public static List<JTextArea> bitAreas;
	public static boolean[] bits;

	public BitSnapShotPanel(ActionListener publicListener) {
		super(publicListener);
	}

	public static void updateBitSnapshot(int newValue) {
		BitSnapShotPanel.bits = BitManipulations.getBits(newValue);
		boolean[] printConventionBits = BitManipulations.getBits(newValue);
		
	    //Reverse array	for printing to GUI
	    boolean tempValue;
	    for (int i = 0; i < bits.length/2; i++) { 
	    	tempValue = printConventionBits[i];
	    	printConventionBits[i] = printConventionBits[printConventionBits.length-1-i];
	    	printConventionBits[printConventionBits.length-1-i] = tempValue;
        }
		
		int count = 0;
		for (JTextArea area : bitAreas) {
			area.setText(String.valueOf(printConventionBits[count] ? 1 : 0));
			count++;
		}
	}

	/*************************************
	 * INITIALISATION
	 */

	@Override
	public void instantiateComponents() {
		this.setPreferredSize(new Dimension(500, 75));
		this.setBorder(new TitledBorder("Bit Content Snapshot"));
		bitAreas = new ArrayList<JTextArea>();
		JTextArea area;
		Font f = new Font("Arial", Font.BOLD, 10);
		for (int index = 0; index < 32; index++) {
			area = new JTextArea("0");
			area.setPreferredSize(new Dimension(20, 25));
			area.setFont(f);
			area.setEditable(false);
			bitAreas.add(index, area);
		}
	}

	@Override
	public void addComponentsToPanel() {
		JLabel label;
		int count = 0;
		int bitPosition = 31;
		Font f = new Font("Arial", Font.PLAIN, 8);
		for (JTextArea area : bitAreas) {
			label = new JLabel(String.valueOf(bitPosition));
			label.setFont(f);
			addComponentToPanel(label, count, 0);
			addComponentToPanel(area, count, 1);
			count++;
			bitPosition--;
		}
	}

	@Override
	public void addPublicActionListeners(ActionListener listener) {
		// not used
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

	}

	/*************************************
	 * GETTERS & SETTERS
	 */

	@Override
	public JPanel getPanel() {
		return this;
	}

}
