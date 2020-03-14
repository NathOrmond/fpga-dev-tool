package com.nathanormond.gui.panels.outputterminal.packetsdropped;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;

public class OutputPacketsDroppedPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private JLabel packetsDroppedLabel;
	public static JTextArea packetsDroppedArea;
	
	public OutputPacketsDroppedPanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		packetsDroppedLabel = new JLabel("Packets Dropped : ");
		packetsDroppedArea = new JTextArea();
		
		styleOutputArea();
	}
	
	private void styleOutputArea(){ 
	
		Font f = new Font("Arial", Font.BOLD, 12);
		
		packetsDroppedLabel.setFont(f);
		
		packetsDroppedArea.setFont(f);
		packetsDroppedArea.setPreferredSize(new Dimension(75,20));
		packetsDroppedArea.setEditable(false);
		packetsDroppedArea.setText("0");
		
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(packetsDroppedLabel, 0, 0);
		addComponentToPanel(packetsDroppedArea, 1, 0);
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


}
