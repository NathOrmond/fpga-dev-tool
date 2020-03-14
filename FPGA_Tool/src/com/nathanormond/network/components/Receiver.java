package com.nathanormond.network.components;

import java.io.IOException;
import java.net.DatagramPacket;

public class Receiver {
	
	public byte[] receiveData;
	public DatagramPacket receivePacket;
	public String rx;
	private static final int MAX_UDP_SIZE = 65535;
	
	/**
	 * initialises expected receive packet structure
	 */
	public Receiver() {
		receiveData = new byte[MAX_UDP_SIZE];							/*Assumes Maximum UDP packet size 64KB*/
		receivePacket = new DatagramPacket(this.receiveData, this.receiveData.length);
	}
	
	/**
	 * receives packets over the network
	 * @throws IOException 
	 */
	public void receivePacket(MySocket socket) throws IOException{ 
		socket.serverSocket.receive(this.receivePacket);
	}

}
