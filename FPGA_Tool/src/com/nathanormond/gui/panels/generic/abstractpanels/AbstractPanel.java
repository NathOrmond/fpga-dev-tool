package com.nathanormond.gui.panels.generic.abstractpanels;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;


/**
 * 13/11/2018
 * @author Nathan Ormond
 *
 *This class is a way of structuring and getting out the 
 *immense amount of boiler plate code necessary to Java Swing GUI.
 */
public abstract class AbstractPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = -6349337196133415391L;
	public GridBagConstraints gbc; 
	public GridBagLayout mgr;
	public JPanel nestedPanel;
	public List<JPanel> panels;
	
	/****************************************************************
	 *		CONSTRUCTOR OVERLOADING
	 */
	

	/**
	 * Default constructor
	 * @param publicListener
	 */
	public AbstractPanel(ActionListener publicListener) {
		createLayoutManager();
		instantiateComponents();
		setActionCommands();
		addLocalActionListeners();
		addPublicActionListeners(publicListener);
		addComponentsToPanel();
	}
	
	/**
	 * Used for classes with nested public action listeners for nested panels
	 * 
	 * Note *Does not instantiate components therefore assumes Panel is ONLY being
	 * 		 used to present multiple other panels.
	 * 
	 * 		If other components are instantiated must be done within addPublicActionListeners()
	 * 		If confusing or redundant probably don't use this one?
	 * 
	 * @param publicListener
	 * @param nestedPublicListeners
	 */
	public AbstractPanel(ActionListener publicListener, boolean nestedPublicListeners) {
		createLayoutManager();
		addPublicActionListeners(publicListener);
		addLocalActionListeners();
		setActionCommands();
		addComponentsToPanel();
	}
	
	/**
	 * Used for classes with nested panels which are being added to a single panel
	 */
	public AbstractPanel(List<JPanel> panels, ActionListener publicListener) {
		this.panels = panels;
		createLayoutManager();
		instantiateComponents();
		setActionCommands();
		addLocalActionListeners();
		addPublicActionListeners(publicListener);
		addComponentsToPanel();
	}
	
	
	/**
	 *****************************************************************/
	
	/**
	 * creates /sets the layout manager for HCI
	 */
	public void createLayoutManager() { 
		gbc = new GridBagConstraints();
		mgr = new GridBagLayout(); 
		this.setLayout(mgr);
	}
	
	public abstract void instantiateComponents();
	public abstract void addComponentsToPanel();
	public abstract void addPublicActionListeners(ActionListener listener);
	public abstract void addLocalActionListeners();
	public abstract void setActionCommands();
	
	/**
	 * adds new component to panel dependent on x and y grid 
	 * (GridBagConstraint Layout mgr)
	 * @param c
	 * @param x
	 * @param y
	 */
	public void addComponentToPanel(Component c, int x, int y){ 
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		mgr.setConstraints(c, gbc);
		add(c);
	}
	
	/**
	 * @return this JPanel
	 */
	public abstract JPanel getPanel();
	
	
	/**
	 * Getters & Setters
	 **/
	
	public List<JPanel> getPanels() {
		return panels;
	}

	public void setPanels(List<JPanel> panels) {
		this.panels = panels;
	}
	
	public JPanel getComponentPanelAtIndex(int index) {
		return getPanels().get(index);
	}

}