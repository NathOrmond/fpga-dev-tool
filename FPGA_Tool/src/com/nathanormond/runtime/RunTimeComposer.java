package com.nathanormond.runtime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

import com.nathanormond.gui.frames.MainFrame;
import com.nathanormond.gui.panels.generic.configurationmenu.ConfigurationPanel;
import com.nathanormond.gui.panels.generic.logoutput.LogOutputPanel;
import com.nathanormond.gui.panels.generic.mainmenu.MainMenuPanel;
import com.nathanormond.gui.panels.generic.mainpanel.ExpositionPanel;
import com.nathanormond.obsrv.ActionCommands;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.obsrv.observable.ObservableReferences;
import com.nathanormond.runtime.runmodes.BuildProgrammerMode;
import com.nathanormond.runtime.runmodes.CommandMode;
import com.nathanormond.runtime.runmodes.TapOnMode;
import com.nathanormond.xml.XMLParser;

/**
 * 13/11/2018
 * 
 * @author Nathan Ormond
 */

public class RunTimeComposer implements ActionListener {

	private static MainFrame mainFrame;
	private ExpositionPanel expositionPanel;
	private List<JPanel> panels;
	private static MainMenuPanel menu;
	private ConfigurationPanel configurationPanel;
	private CommandMode commandMode;
	private TapOnMode tapOnMode;
	private BuildProgrammerMode buildProgrammerMode;
	private LogOutputPanel logOutputPanel;

	/*************************************************
	 * Initialisation
	 */

	/**
	 * Constructor: Upon initialisation set up all relevant objects then set the
	 * main frame to the menu panel.
	 */
	public RunTimeComposer() {
		initialise(); // set up methods (just for clarity contained in single method)
		createMainPanel(menu.getPanel());
		mainFrame = new MainFrame(expositionPanel.getPanel(), this); // initialise frame to main menu
	}
	
	public RunTimeComposer(String test) {
	}

	/**
	 * Set default XML to running configurations' path. Configure tool based off of
	 * XML. Set up GUI (Frame and Panel objects).
	 */
	private void initialise() {
		setDefaultXMLConfig();
		setInitialObservableVarValues();
		runNewXMLConfig();
	}
	

	/**
	 * Sets global variable xml path string to default.
	 */
	public void setDefaultXMLConfig() {
		ObservableFacade.setValue(ORNames.XML_CONFIG_PATH, ObservableReferences.internalXMLRelativePath);
	}

	/**
	 * Reads and parses XML file which has been set at GlobalPointers.xmlPath.
	 * 
	 * Prints error to console : Most likely error case xml does not conform to
	 * schema. However check stack trace.
	 */
	private void runNewXMLConfig() {
		try {
			new XMLParser();
		} catch (JAXBException e) {
			e.printStackTrace(); // If hitting error here check initial XML path (ObservableReferences) is correct
		}
		instantiateGUIObjects();
	}

	/**
	 * Instantiates the objects used by the GUI: The main frame, configuration
	 * panel.
	 */
	private void instantiateGUIObjects() {
		configurationPanel = new ConfigurationPanel(this);
		menu = new MainMenuPanel(this, "Run time composers Main Menu Panel");
		commandMode = new CommandMode();
		logOutputPanel = new LogOutputPanel(this);
		tapOnMode = new TapOnMode();
		buildProgrammerMode = new BuildProgrammerMode();
	}


	/*************************************************
	 * Panel Combiner
	 */

	private void createMainPanel(JPanel featuredPanel) {
		panels = new ArrayList<JPanel>();
		panels.add(featuredPanel);
		expositionPanel = new ExpositionPanel(panels, this);
	}

	/*************************************************
	 * Composer Switch
	 */

	/**
	 * Action command switch: Outputs action command to console and then carries out
	 * subroutine as action command dictates. Acts as switch for events.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ActionCommands.SELECT_COMMAND_MODE:
			commandMode();
			break;
		case ActionCommands.SELECT_TAP_ON_MODE:
			tapOnMode();
			break;
		case ActionCommands.SELECT_FPGA_PROGRAMMER_MODE:
			fpgaProgrammerMode();
			break;
		case ActionCommands.GUI_HOME:
			setHomeMenu();
			break;
		case ActionCommands.CONFIG_MENU_ITEM:
			configMenuItemSubRoutine();
			break;
		case ActionCommands.LOG_OUTPUT_MENU_ITEM:
			logOutputMenuItemSubRoutine();
			break;
		}
		mainFrame.updateFrame(expositionPanel.getPanel());
		System.out.println("GUI UPDATED");
		consoleOutputBreak();
	}

	/*************************************************
	 * Switch methods
	 */

	private void logOutputMenuItemSubRoutine() {
		createMainPanel(logOutputPanel.getPanel());
	}

	private void configMenuItemSubRoutine() {
		createMainPanel(configurationPanel.getPanel());
		commandMode = new CommandMode();
	}

	private void setHomeMenu() {
		createMainPanel(menu.getPanel());
	}

	private void fpgaProgrammerMode() {
		createMainPanel(buildProgrammerMode.getBuildProgrammerPanel());
	}

	private void tapOnMode() {
		createMainPanel(tapOnMode.getTapOnTestPanel());
	}

	private void commandMode() {
		createMainPanel(commandMode.getCommandModePanel());

	}
	
	/***************************************************
	 * OBSERVER METHODS
	 */
		
	/**
	 * Set the initial default values for Observable variables
	 * Set here the initial values for observable variables which are
	 * NOT REFERENCED OR CONFIGURABLE THROUGH THE XML
	 */
	public void setInitialObservableVarValues(){ 
		ObservableFacade.setValue(ORNames.CORE_TEMPERATURE_DATASET , 0f);
		ObservableFacade.setValue(ORNames.LTM_TEMP_DATASET , 0f);
		ObservableFacade.setValue(ORNames.MAX_TEMP_DATASET , 0f);
		ObservableFacade.setValue(ORNames.MIN_TEMP_DATASET , 0f);
		ObservableFacade.setValue(ORNames.PEC_TEMP_DATASET , 0f);	
		ObservableFacade.setValue(ORNames.CORE_VOLTAGE_DATASET, 0f);
		ObservableFacade.setValue(ORNames.IS_DELAY_TIME_SET , false);
		ObservableFacade.setValue(ORNames.COMMAND_DELAY , 1);
		ObservableFacade.setValue(ORNames.IS_NUM_COMMANDS_SET , false);
		ObservableFacade.setValue(ORNames.NUM_COMMANDS_TO_SEND , 0);
		ObservableFacade.setValue(ORNames.SELECTED_START_COMMAND , "NONE");
		ObservableFacade.setValue(ORNames.SELECTED_STOP_COMMAND , "NONE");
		ObservableFacade.setValue(ORNames.IS_NETWORK_IN_USE, false);
		ObservableFacade.setValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK , false);
		ObservableFacade.setValue(ORNames.RUN_UDP_TAPONMODE_NETWORK , false);
		ObservableFacade.setValue(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, false);
		ObservableFacade.setValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT , false);
		ObservableFacade.setValue(ORNames.SELECTED_FLASH_SLOT , "region 0");
		ObservableFacade.setValue(ORNames.OUTPUT_LOG_PATH , "NONE");
		ObservableFacade.setValue(ORNames.IS_DELAY_TIME_SET , false);
		ObservableFacade.setValue(ORNames.LOG_TO_OUTPUT , false);
		ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING , "NULL");
		ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, 0);
	}


	/*************************************************
	 * Generic methods
	 */

	private void consoleOutputBreak() {
		System.out.println("=========================================");
	}


}
