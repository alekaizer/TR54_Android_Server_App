package com.utbm.lo52.tr54server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 
 */

/**
 * @author lekaizer
 *
 */
public class TrafficLights extends Thread {
	private byte[] buffer = new byte[4];
	private DatagramSocket sendingSocket; 
	
	private DatagramPacket sendPacket;
	private InetAddress ipAddress;
	private int dPort;
	
	public TrafficLights(InetAddress ipAddress, int dPort) throws SocketException{
		this.ipAddress = ipAddress;
		this.dPort = dPort;
		sendingSocket = new DatagramSocket();
				
	}
	
	public void run(){
		while(true){
			buffer = "1".getBytes();
			sendPacket = new DatagramPacket(buffer, buffer.length, ipAddress, dPort);
			try {
				sendingSocket.send(sendPacket);
				Thread.sleep(2000);
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buffer = "2".getBytes();
			sendPacket.setData(buffer);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
