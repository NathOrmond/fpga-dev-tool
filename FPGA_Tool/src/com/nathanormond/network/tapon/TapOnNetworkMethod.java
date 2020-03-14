package com.nathanormond.network.tapon;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

import com.nathanormond.fileio.WriteFileThread;
import com.nathanormond.network.AbstractUDPNetworkMethod;
import com.nathanormond.network.components.MySocket;
import com.nathanormond.network.components.Receiver;
import com.nathanormond.obsrv.observable.ORNames;

public class TapOnNetworkMethod extends AbstractUDPNetworkMethod {

	
	/*************************************
	 * INSTANTIATION
	 */
	
	private int errorCount;
	private static int fileWriteCacheBuffer = 2000;
	
	
	public TapOnNetworkMethod() {
		super(fileWriteCacheBuffer);
	}
	
	/**
	 * Creates socket object
	 * creates receiver 
	 * creates txu now (as start and stop command will always be daon for this not selected by user in gui)
	 */
	@Override
	public void instantiateNetworkObjects() {
		socket = new MySocket(); 
		rxu = new Receiver(); 
		setTxuCommand("DATA ON", "DATA OFF");
		System.out.println("Network Ready : TAP ON");
		System.out.println(consoleOutputBreak());
	}
	
	
	/*************************************
	 * NETWORK LOOP
	 */
	
	/**
	 * Resets the count
	 * opens the socket and transmits for data on
	 */
	@Override
	public void beforeNetworkLoop() {
		System.out.println("###### TAP ON NETWORK STARTED: ");
		errorCount = 0;
		setObservableVar(ORNames.RUN_UDP_TAPONMODE_NETWORK, true);
		WriteFileThread.clearCache();
		setCount(1);
		setObservableVar(ORNames.SEQ_COUNT, 0);
		int myPort = (int) getObserver(ORNames.MY_UDP_PORT).getVariableInstance();
		socket.openSocket(myPort);
		transmitTapOnStart();
	}
	
	
	private void transmitTapOnStart() {
		txu.sendOpenPacket(socket);
	}
	
	/**
	 * returns runUDPTapOnTest (TRUE/FALSE)
	 * FALSE if stop button pressed
	 */
	@Override
	public boolean networkLoopCondition() {
		return (boolean) getObserver(ORNames.RUN_UDP_TAPONMODE_NETWORK).getVariableInstance();
	}

	/**
	 * 
	 */
	@Override
	public void networkLoop() {
		setObservableVar(ORNames.PREV_SEQ_COUNT, getObservableVar(ORNames.SEQ_COUNT));
		try {
			rxu.receivePacket(socket); /* Listen for FPGA response */
		} catch (SocketTimeoutException e) {
			System.out.println("NO RESPONSE, CONNECTION TIMED OUT");
		} catch (IOException e) {
			e.printStackTrace();
		}
		TapOnPacketAnalysis.tapOnModeOutputString(ByteBuffer.wrap(rxu.receiveData), getCount(), errorCount);
		errorCountCheck();
		setCount(getCount()+1);
	}
	
	@Override
	public void updateOutputGraphics() {
		// not used
	}
	
	/**
	 * 
	 */
	public void errorCountCheck() {
		int seqCount = getObservableVar(ORNames.SEQ_COUNT);
		int prevSeqCount = getObservableVar(ORNames.PREV_SEQ_COUNT);
		if ((seqCount != 0) && (getCount() > 10)) {
			
			if ((prevSeqCount + 1) != (seqCount)) {
				errorCount += (seqCount - prevSeqCount);
			}
			
		}
	}

	/**
	 * 
	 */
	@Override
	public void afterNetworkLoop() {
		txu.sendClosePacket(socket);
		socket.closeSocket();
		System.out.println("###### TAP ON NETWORK STOPPED: ");
	}
	
	
	public int getErrorCount() {
		return errorCount;
	}

}
