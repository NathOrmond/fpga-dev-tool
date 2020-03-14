package com.nathanormond.network.tapon;

import java.nio.ByteBuffer;

import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class TapOnPacketAnalysis {
	
	
	/*****************************************************************
	 *	TAP ON MODE ANALYSIS
	 **/
	
	/**
	 * 
	 * @param bb
	 * @param count
	 * @param errorCount
	 * @return
	 */
	public static String tapOnModeOutputString(ByteBuffer bb, float count, float errorCount) { 
		ObservableFacade.setValue(ORNames.SEQ_COUNT, bb.getInt(ObservableFacade.getValue(ORNames.PACKET_SEQUENCE_NUMBER)));
		
		return "Update: " + count + "\n-----------------------------------------------------------------\n"
			 + "Error : " + errorCount + "\n-----------------------------------------------------------------\n"
			 + "SEQUENCE_COUNT_DEC : " + ObservableFacade.getValue(ORNames.SEQ_COUNT) + "\n"
			 + "SEQUENCE_COUNT_HEX : " + ObservableFacade.getValue(ORNames.SEQ_COUNT) + "\n";
	}
	
	/**
	*****************************************************************/

}
