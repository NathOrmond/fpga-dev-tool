package com.nathanormond.network.components;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MySocket {

	public DatagramSocket serverSocket;
	private static int timeout = 1000;
	private int myPort;

	/**
	 * opens a socket on port
	 * @return
	 */
	public boolean openSocket(int myPort) {
		try {
			this.myPort = myPort;
			this.serverSocket = new DatagramSocket(this.myPort);
			this.serverSocket.setSoTimeout(timeout);
			this.serverSocket.setReceiveBufferSize(99999999);
			System.out.printf("Listening on udp:%s:%d:%n", InetAddress.getLocalHost().getHostAddress(), this.myPort);
			return true;
		} catch (SocketException e) {
			e.printStackTrace();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * closes my socket
	 * 
	 * @return
	 */
	public boolean closeSocket() {
		this.serverSocket.close();
		return true;
	}

}
