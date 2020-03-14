package com.nathanormond.network.commandmode;

import java.nio.ByteBuffer;

import com.nathanormond.bitmanipulations.BitManipulations;
import com.nathanormond.fileio.WriteFileThread;
import com.nathanormond.gui.panels.outputterminal.commandmode.loopback.OutputLoopBackTestPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class LoopBackModePacketAnalysis { 
	
	
	/*****************************************************************
	 *	LOOP BACK MODE ANALYSIS
	 **/
	
	
	/**
	 * 
	 * @param bb
	 * @param count
	 * @return
	 */
	public static void loopBackOutputString(ByteBuffer bb, float count) {
		
		int packetContent = bb.getInt( (int) ObservableFacade.getValue(ORNames.CORE_TEMP_T0));
		float coreTemp = BitManipulations.calculateTemperature(packetContent);
		ObservableFacade.setValue(ORNames.CORE_TEMPERATURE_DATASET, coreTemp);

		String output = lineBrk() 
				+ String.format("UPDATE : %s \n", count)
				+ String.format("PACKET HEADER : %s \n",BitManipulations.hexToString(BitManipulations.getHexPrint(bb, ObservableFacade.getValue(ORNames.PACKET_HEADER))))
				+ lineBrk()
				+ String.format("SEQUENCE_COUNT HEX : %s \n",BitManipulations.getHexPrint(bb, ObservableFacade.getValue(ORNames.PACKET_SEQUENCE_NUMBER)))
				+ lineBrk()
				+ String.format("BITE MODE HEX : %s \n",BitManipulations.hexToString(BitManipulations.getHexPrint(bb, ObservableFacade.getValue(ORNames.BITE_MODE))))
				+ lineBrk()
				+ String.format("CHAN_A HEX : %s \n",BitManipulations.getHexPrint(bb, ObservableFacade.getValue(ORNames.CHAN_A_BITE_RES)))
				+ String.format("CHAN_B HEX : %s \n",BitManipulations.getHexPrint(bb, ObservableFacade.getValue(ORNames.CHAN_B_BITE_RES)))
				+ String.format("CHAN_C HEX : %s \n",BitManipulations.getHexPrint(bb, ObservableFacade.getValue(ORNames.CHAN_C_BITE_RES)))
				+ String.format("CHAN_D HEX : %s \n",BitManipulations.getHexPrint(bb, ObservableFacade.getValue(ORNames.CHAN_D_BITE_RES)))
				+ lineBrk()
				+ String.format("THER :  %s \n",BitManipulations.hexToString(BitManipulations.getHexPrint(bb, ObservableFacade.getValue(ORNames.THER))))
				+ String.format("Core Temp : %s degrees C \n", coreTemp) + lineBrk();

		OutputLoopBackTestPanel.loopBackOutputTextArea.setText(output);
		WriteFileThread.addToCache(output);
	}
	
	private static String lineBrk() { 
		return "--------------------------------------------- \n";
	}
	
	
	
	/**
	*****************************************************************/

}
