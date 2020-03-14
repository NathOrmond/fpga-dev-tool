package com.nathanormond.network.commandmode;

import java.nio.ByteBuffer;
import java.util.List;

import com.nathanormond.bitmanipulations.BitManipulations;
import com.nathanormond.fileio.WriteFileThread;
import com.nathanormond.gui.components.textarea.TrueFalseArea;
import com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.OutputFullPacketDisplayPanel;
import com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.generic.BitSnapShotPanel;
import com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.hotlink.HotLinkStatusPanel;
import com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.packet.PacketInfoPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class FullPacketContentAnalysis { 
	
	private static final double VOLTAGE_COEFF_1 =  0.001465;
	private static final double VOLTAGE_COEFF_2 =  0.001465;
	private static final double VOLTAGE_COEFF_3 =  0.000488;
	private static final double VOLTAGE_COEFF_4 =  0.0007324;
	
	private static float t = -40;
	
	/**
	 * Instigates the update of all GUI objects on output console for full packet analysis mode
	 * @param bb
	 * @param count
	 */
	public static void modifyOutputs(ByteBuffer bb, float count) {
		OutputFullPacketDisplayPanel.counter.setText(String.valueOf(count));
		fullOutput(bb);
		hotLinkStatus(bb);
		temperatures(bb);
		voltages(bb);
	}
	
	/**
	 * Updates temperatures on the temperatures info panel of the command mode output console
	 * @param bb
	 */
	private static void temperatures(ByteBuffer bb) { 
//		float temp = t; //Debug thermometer Uncomment these and comment out 'temp = ...' code to see cool incrementing graphics.
//		t += 10;
		
		int packetContent = bb.getInt( (int) ObservableFacade.getValue(ORNames.CORE_TEMP_T0));
		float temp = BitManipulations.calculateTemperature(packetContent);
		ObservableFacade.setValue(ORNames.CORE_TEMPERATURE_DATASET, temp);

		packetContent = bb.getInt( (int) ObservableFacade.getValue(ORNames.LTM_TEMP));
		temp = BitManipulations.calculateTemperature(packetContent);
		ObservableFacade.setValue(ORNames.LTM_TEMP_DATASET, temp);
		
		packetContent = bb.getInt( (int) ObservableFacade.getValue(ORNames.PEC_TEMP));
		temp = BitManipulations.calculateTemperature(packetContent);
		ObservableFacade.setValue(ORNames.PEC_TEMP_DATASET, temp);
		
		packetContent = bb.getInt( (int) ObservableFacade.getValue(ORNames.MAX_TEMP));
		temp = BitManipulations.calculateTemperature(packetContent);
		ObservableFacade.setValue(ORNames.MAX_TEMP_DATASET, temp);
		
		packetContent = bb.getInt( (int) ObservableFacade.getValue(ORNames.MIN_TEMP));
		temp = BitManipulations.calculateTemperature(packetContent);
		ObservableFacade.setValue(ORNames.MIN_TEMP_DATASET, temp);
	}
	
	/**
	 * This method updates the "Voltages Info" tab on the command mode output console
	 * @param bb
	 */
	private static void voltages(ByteBuffer bb) { 
		double voltage = BitManipulations.calculateVoltage(bb, ObservableFacade.getValue(ORNames.CORE_TEMP_T0), VOLTAGE_COEFF_4);
		ObservableFacade.setValue(ORNames.CORE_VOLTAGE_DATASET, voltage);
	}
	
	/**
	 * This method updates the "Hot Link Info" tab on the command mode output console
	 * @param bb
	 */
	private static void hotLinkStatus(ByteBuffer bb) {
		System.out.println(BitManipulations.padLeft(bb.getInt(ObservableFacade.getValue(ORNames.MEZANINE_2_BITE_FLAGS))));
		BitSnapShotPanel.updateBitSnapshot(bb.getInt(ObservableFacade.getValue(ORNames.MEZANINE_2_BITE_FLAGS)));
		
		updateTrueFalseArea(HotLinkStatusPanel.hotLinkFailAreas, 0);
		updateTrueFalseArea(HotLinkStatusPanel.hotLinkPoorAreas, 4);
		updateTrueFalseArea(HotLinkStatusPanel.hotLinkLossAreas, 8);
		
	}
	
	/**
	 * This method updates the True false areas of the "Hot Link info"
	 * tab on the command mode otuput console.
	 * @param areas
	 * @param startBit
	 */
	private static void updateTrueFalseArea(List<TrueFalseArea> areas, int startBit) { 
		for(int index = 0; index < 4; index++) { 
			areas.get(index).setText(String.valueOf(BitSnapShotPanel.bits[(index + startBit)]));
		}
	}
	
	/**
	 * This method populates the full output tab of the command mode output panel
	 * @param bb
	 */
	private static void fullOutput(ByteBuffer bb) { 
		PacketInfoPanel.packetHeaderArea.setText(BitManipulations.hexToString(BitManipulations.getHexPrint(bb, ObservableFacade.getValue(ORNames.PACKET_HEADER))));
		PacketInfoPanel.therArea.setText(BitManipulations.hexToString(BitManipulations.getHexPrint(bb, ObservableFacade.getValue(ORNames.THER))));
		PacketInfoPanel.fullOutputDumpArea.setText(packetFullHexOutput(bb));
	}
	
	/**
	 * This method reads the full rx packet and creates a string
	 * @param bb
	 * @return fullPacketContentString
	 */
	private static String packetFullHexOutput(ByteBuffer bb) { 
		// Up to NAS Data in Packet
		String rv = "OFFSET   -  VALUE   - ASCII   \n";
		for(int index = 0; index <= 200; index += 4) { 
			String hexIndex = BitManipulations.padLeft(index);
			String hexTargetValue = BitManipulations.getHexPrint(bb, index);
			rv = rv + hexIndex + " - " + hexTargetValue  + " - " + BitManipulations.hexToString(hexTargetValue) + "\n";
		}	
		
		WriteFileThread.addToCache(rv);
		
		return rv;
	}
	
	
// 	 Addidional Voltages not currently used
//	 5V Input Supply : PacketReader.calculateVoltage(bb, GlobalPointers.INPUT_5V, VOLTAGE_COEFF_1) 
//	 3V3 Input Supply :  PacketReader.calculateVoltage(bb, GlobalPointers.INPUT_3V3, VOLTAGE_COEFF_2)
//	 1V5 Input Supply :  PacketReader.calculateVoltage(bb, GlobalPointers.INPUT_1V5, VOLTAGE_COEFF_2) 
//	 1V2 Input Supply :   PacketReader.calculateVoltage(bb, GlobalPointers.INPUT_1V2, VOLTAGE_COEFF_3) 
//	 10V8 GTX :   PacketReader.calculateVoltage(bb, GlobalPointers.INPUT_1V8_GTX, VOLTAGE_COEFF_3) 
//	 1V0 GTX :   PacketReader.calculateVoltage(bb, GlobalPointers.INPUT_1V0_GTX, VOLTAGE_COEFF_3)
//	 1V2 GTX : " PacketReader.calculateVoltage(bb, GlobalPointers.INPUT_1V2_GTX, VOLTAGE_COEFF_3)
	
	
	
	/******************************
	 * GETTERS & SETTERS
	 */
	
	public static double getVoltageCoeff1() {
		return VOLTAGE_COEFF_1;
	}


	public static double getVoltageCoeff2() {
		return VOLTAGE_COEFF_2;
	}


	public static double getVoltageCoeff3() {
		return VOLTAGE_COEFF_3;
	}


	public static double getVoltageCoeff4() {
		return VOLTAGE_COEFF_4;
	}
	 

	
}
