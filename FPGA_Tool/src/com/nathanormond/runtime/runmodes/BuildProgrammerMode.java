package com.nathanormond.runtime.runmodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JPanel;

import com.nathanormond.gui.panels.services.buildprogrammer.BuildProgrammerPanel;
import com.nathanormond.gui.panels.services.buildprogrammer.subpanels.ConsoleWindowPanel;
import com.nathanormond.gui.panels.services.buildprogrammer.subpanels.InterrogatePanel;
import com.nathanormond.gui.panels.services.buildprogrammer.subpanels.ProgressPanel;
import com.nathanormond.gui.panels.services.buildprogrammer.subpanels.SelectWorkingRegionPanel;
import com.nathanormond.gui.panels.services.buildprogrammer.subpanels.WorkingRegionOptionsPanel;
import com.nathanormond.network.buildprogrammer.DownloadBuildNetworkMethod;
import com.nathanormond.network.buildprogrammer.EraseRegionNetworkMethod;
import com.nathanormond.network.buildprogrammer.ExecuteRegionNetworkMethod;
import com.nathanormond.network.buildprogrammer.PollRegionNetworkMethod;
import com.nathanormond.network.buildprogrammer.RunningBuildNetworkMethod;
import com.nathanormond.obsrv.ActionCommands;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class BuildProgrammerMode extends AbstractModeComposer {

	private static BuildProgrammerPanel buildProgrammerPanel;
	private List<JPanel> panels;
	private static SelectWorkingRegionPanel selectWorkingRegionPanel;
	private static InterrogatePanel interrogatePanel;
	private static ConsoleWindowPanel consoleWindowPanel;
	private static WorkingRegionOptionsPanel workingRegionOptionsPanel;
	private static ProgressPanel progressPanel;

	/********************************
	 * Initialisation
	 */

	@Override
	public void instantiateObjects() {
		panels = new ArrayList<JPanel>();

		selectWorkingRegionPanel = new SelectWorkingRegionPanel(this);
		interrogatePanel = new InterrogatePanel(this);
		consoleWindowPanel = new ConsoleWindowPanel(this);
		workingRegionOptionsPanel = new WorkingRegionOptionsPanel(this);
		progressPanel = new ProgressPanel(this);

		panels.add(selectWorkingRegionPanel.getPanel());
		panels.add(interrogatePanel.getPanel());
		panels.add(consoleWindowPanel.getPanel());
		panels.add(workingRegionOptionsPanel.getPanel());
		panels.add(progressPanel.getPanel());

		buildProgrammerPanel = new BuildProgrammerPanel(panels, this);
		
		ObservableFacade.addObserver(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, this);
		
	}

	/********************************
	 * Action Switch
	 */

	@Override
	public void actionSwitch(String actionCommand) {
		System.out.println(actionCommand);
		
		if (actionCommand.matches("\\d+")) {	//check action is change of build region
			ObservableFacade.setValue(ORNames.SELECTED_FLASH_SLOT, String.format("region %d", Integer.parseInt(actionCommand)));
		} else {

			ObservableFacade.setValue(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, true);
			ObservableFacade.setValue(ORNames.IS_NETWORK_IN_USE, true);
			ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, 0);
			
			switch (actionCommand) {
				
				/** interrogation commands **/
				case ActionCommands.INTERROGATE_RUNNING_REGION:
					interrogateRunning();
					break;
					
				case ActionCommands.INTERROGATE_SELECTED_REGION:
					interrogateSelectRegion();
					break;
					
				case ActionCommands.INTERROGATE_ALL_REGIONS:
					interrogateAllRegions();
					break;
					
				/** working region options **/
				case ActionCommands.ERASE_REGION:
					eraseRegion();
					break; 
					
				case ActionCommands.SET_RUNNING:
					setRunningRegion();
					break;
					
				case ActionCommands.UPLOAD_NEW_BUILD:
					uploadNewBuild();
					break;
			}


			ObservableFacade.setValue(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, false);
			ObservableFacade.setValue(ORNames.IS_NETWORK_IN_USE, false);
		}
	}

	/********************************
	 * Action Methods
	 */

	private void interrogateRunning() {
		RunningBuildNetworkMethod runningNetwork = new RunningBuildNetworkMethod(2000);
		Thread t = new Thread(runningNetwork);
		t.start();
	}

	private void interrogateSelectRegion() {
		PollRegionNetworkMethod pollRegionNetwork = new PollRegionNetworkMethod(2000);
		Thread t = new Thread(pollRegionNetwork);
		t.start();
	}

	private void interrogateAllRegions() {
		PollRegionNetworkMethod pollRegionNetwork = new PollRegionNetworkMethod(2000);
		
		String selectedSlot = ObservableFacade.getValue(ORNames.SELECTED_FLASH_SLOT);
		List<String> returnedValues = new ArrayList<>();
		
		for(int index = 0; index < (int)ObservableFacade.getValue(ORNames.NUM_BUILD_SLOTS); index++) { 
			ObservableFacade.setValue(ORNames.SELECTED_FLASH_SLOT, String.format("region %d", index));
			pollRegionNetwork.run();
			returnedValues.add(ObservableFacade.getValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING));
			ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, ((index / (int)ObservableFacade.getValue(ORNames.NUM_BUILD_SLOTS)) * 100));
		}
		
		String outputStr = "";
		int count = 0;
		for(String str : returnedValues) { 
			outputStr += String.format("region %d \n %s \n =========================== \n", count,str);
			count++;
		}
		
		ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, outputStr);
		
		ObservableFacade.setValue(ORNames.SELECTED_FLASH_SLOT, selectedSlot);
		ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS,100);
	}


	private void eraseRegion() {
		EraseRegionNetworkMethod eraseRegion = new EraseRegionNetworkMethod(2000);
		Thread t = new Thread(eraseRegion);
		t.start();
	}

	private void setRunningRegion() {
		ExecuteRegionNetworkMethod executeRegion = new ExecuteRegionNetworkMethod(2000);
		Thread t = new Thread(executeRegion);
		t.start();
		consoleWindowPanel.setConsoleText(String.format("%s :: set running", (String) ObservableFacade.getValue(ORNames.SELECTED_FLASH_SLOT)));
	}

	private void uploadNewBuild() {
		DownloadBuildNetworkMethod downloadMethod = new DownloadBuildNetworkMethod(2000);
		Thread t = new Thread(downloadMethod);
		t.start();
	}

	/********************************
	 * GETTERS & SETTERS
	 */

	public JPanel getBuildProgrammerPanel() {
		return buildProgrammerPanel.getPanel();
	}

	@Override
	public void update(Observable obs, Object obj) {
		consoleWindowPanel.setConsoleText((String) obj);
	}

}
