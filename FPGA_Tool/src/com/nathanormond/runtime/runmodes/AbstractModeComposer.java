package com.nathanormond.runtime.runmodes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

public abstract class AbstractModeComposer implements ActionListener, Observer {

	/********************************
	 * Initialisation
	 */

	public AbstractModeComposer() {
		instantiateObjects();
	}
	
	public abstract void instantiateObjects();
	
	
	/********************************
	 * Action Switch
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		actionSwitch(e.getActionCommand());
	}
	
	public abstract void actionSwitch(String actionCommand);
}
