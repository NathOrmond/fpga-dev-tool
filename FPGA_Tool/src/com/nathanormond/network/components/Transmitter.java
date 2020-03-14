package com.nathanormond.network.components;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.List;

import com.nathanormond.bitmanipulations.BitManipulations;



public class Transmitter {

	private ByteBuffer bb;
	private byte[] sendOpenData, sendCloseData;
	private DatagramPacket sendOpenPacket, sendClosePacket;
	private String ipAddress;
	private int fpgaPort;

	public Transmitter(List<String> sendCommand, List<String> closeCommand, String ipAddress, int fpgaPort) throws IOException {
		sendOpenData = parseHexAndASCIICombinedCommands(sendCommand);
		sendCloseData = parseHexAndASCIICombinedCommands(closeCommand);
		this.fpgaPort = fpgaPort;
		this.ipAddress = ipAddress;
		createOpenClosePackets();
	}
	
	public Transmitter(String ipAddress, int fpgaPort) throws IOException {
		this.fpgaPort = fpgaPort;
		this.ipAddress = ipAddress;
	}

	/**
	 * Java wants to sign bytes - however if a line of hex
	 * comes in from the XML command set as 0xCC 
	 * the byte cc must be sent via UDP (no signature extra bytes) 
	 * Commands also include ASCII to be sent via UDP, this must not be confused
	 * for hex : this method attempts to parse commands to the correct format
	 * to be sent via UDP.
	 * 
	 * @param xmd
	 * @return byteArrayForUDP
	 * @throws IOException
	 */
	public byte[] parseHexAndASCIICombinedCommands(List<String> xmd) throws IOException {
		
		bb = ByteBuffer.allocate(1024);
		

		for(String line : xmd) { 
			
			if (line.contains("0x")) { 					//	Check if line is hex command or ASCII
				line = line.replace("0x", "");			//	for Hex
				byte[] bytes = javax.xml.bind.DatatypeConverter.parseHexBinary(line);
				bb.put(ByteBuffer.wrap(bytes)); // Unsigned byte array
			} else { 									//	for ASCII
				bb.put(ByteBuffer.wrap(line.getBytes()));
			}

		}
		
		int allocatedBytes = bb.position();
		ByteBuffer resizedBytebuffer = ByteBuffer.allocate(allocatedBytes);
		for(int index = 0; index < allocatedBytes; index++) { 
			resizedBytebuffer.put(bb.get(index));
		}
		
		return resizedBytebuffer.array(); 
	}
	
	
	/**
	 * creates the packet to be sent
	 */
	private void createOpenClosePackets() {
		InetAddress ipAddress;
		try {
			ipAddress = InetAddress.getByName(this.ipAddress);
			this.sendOpenPacket = new DatagramPacket(sendOpenData, sendOpenData.length, ipAddress, this.fpgaPort);
			this.sendClosePacket = new DatagramPacket(sendCloseData, sendCloseData.length, ipAddress, this.fpgaPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	

	/**********************************************************
	 * TRANSMIT UDP MESSAGES
	 */
	
	public void sendCustomUDPPacket(List<String> commandsToSend, MySocket socket) { 
		byte[] messageBytes = null;
		DatagramPacket packet = null;
		
		try {
			messageBytes = parseHexAndASCIICombinedCommands(commandsToSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			packet = new DatagramPacket(messageBytes, messageBytes.length, InetAddress.getByName(this.ipAddress), this.fpgaPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		try {
			socket.serverSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * sends command packet over the network
	 */
	public void sendOpenPacket(MySocket socket) {
		try {
			System.out.println("Sending Packet ....");
			socket.serverSocket.send(this.sendOpenPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sends close conn packet over the network
	 */
	public void sendClosePacket(MySocket socket) {
		try {
			socket.serverSocket.send(this.sendClosePacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
