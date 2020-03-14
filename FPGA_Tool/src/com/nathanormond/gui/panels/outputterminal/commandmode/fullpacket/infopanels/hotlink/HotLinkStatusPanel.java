package com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.hotlink;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import com.nathanormond.gui.components.textarea.TrueFalseArea;
import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;

public class HotLinkStatusPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	
	public static List<JLabel> hotLinkFailTitles;
	public static List<TrueFalseArea> hotLinkFailAreas;
	
	public static List<JLabel> hotLinkPoorTitles;
	public static List<TrueFalseArea> hotLinkPoorAreas;
	
	public static List<JLabel> hotLinkLossTitles;
	public static List<TrueFalseArea> hotLinkLossAreas;
	
	public HotLinkStatusPanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	/*************************************
	 * INITIALISATION
	 */
	
	@Override
	public void instantiateComponents() {
		this.setPreferredSize(new Dimension(500,325));
		this.setBorder(new TitledBorder("HotLinkStatus"));
		
		// Hot Link Fail GUI
		hotLinkFailTitles = new ArrayList<>();
		populateLabelList(hotLinkFailTitles, "FAIL");
		
		hotLinkFailAreas = new ArrayList<>();
		populateTextAreaList(hotLinkFailAreas);
		
		
		// Hot Link Poor GUI
		hotLinkPoorTitles = new ArrayList<>();
		populateLabelList(hotLinkPoorTitles, "POOR");
		hotLinkPoorAreas = new ArrayList<>();
		populateTextAreaList(hotLinkPoorAreas);
		
		
		//Hot Link Loss GUI
		hotLinkLossTitles = new ArrayList<>();
		populateLabelList(hotLinkLossTitles, "LOSS");
		hotLinkLossAreas = new ArrayList<>();
		populateTextAreaList(hotLinkLossAreas);
		
	}
	
	private void populateTextAreaList(List<TrueFalseArea> areas) { 
		TrueFalseArea area;
		for(int index = 0; index < 4; index ++) { 
			area = new TrueFalseArea();
			areas.add(index, area);
		}
	}
	
	private void populateLabelList(List<JLabel> labels, String suffix) { 
		JLabel label;
		char[] chars = {'A','B','C','D'};
		for(int i = 0; i < 4; i ++) { 
			label = new JLabel(chars[i] + " " + suffix);
			label.setPreferredSize(new Dimension(50, 55));
			Font f = new Font("Arial", Font.BOLD, 10);
			label.setFont(f);
			labels.add(label);
		}
	}

	@Override
	public void addComponentsToPanel() {
		addHotLinkListToPanel(hotLinkFailTitles, hotLinkFailAreas, 0);
		addHotLinkListToPanel(hotLinkPoorTitles, hotLinkPoorAreas, 2);
		addHotLinkListToPanel(hotLinkLossTitles, hotLinkLossAreas, 4);

	}
	
	
	private void addHotLinkListToPanel(List<JLabel> labels, List<TrueFalseArea> areas, int xCoOrdinate) { 
		for(int index = 0; index < labels.size(); index ++) { 
			gbc.anchor = GridBagConstraints.EAST;
			addComponentToPanel(labels.get(index), xCoOrdinate, index);
			gbc.anchor = GridBagConstraints.WEST;
			addComponentToPanel(areas.get(index), (xCoOrdinate + 1), index); /**NOTE : When using x co-ordinate of next GBC component must incrememnt +2**/
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
		// not used
	}
	
	
	
	/*************************************
	 * GETTERS & SETTERS
	 */
	
	@Override
	public JPanel getPanel() {
		return this;
	}

}
