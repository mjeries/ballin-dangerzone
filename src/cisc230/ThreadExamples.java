package cisc230;

import java.io.IOException;
import java.net.*;

public class ThreadExamples {

	public static void main(String[] args) {
		System.out.println("Listen to UDP Multicast broadcast for 5 seconds:");
		MulticastListener multicastListener1 = new MulticastListener("5 seconds broadcast listener");
		multicastListener1.setTimer(5000);
		multicastListener1.setInfoMessages(true);
		multicastListener1.start();
		// Normally you would have to start a thread like this:
		// new java.lang.Thread(multicastListener1).start();
		
		try {java.lang.Thread.sleep(7000);} catch (InterruptedException e) {}

		System.out.println("\n--------------------------------------------------" +
				"----------------------------");
		System.out.println("Listen to UDP Multicast broadcast 7 times:");
		MulticastListener multicastListener2 = new MulticastListener("7 times broadcast listener");
		multicastListener2.setCounter(7);
		multicastListener2.setInfoMessages(true);
		multicastListener2.start();
		
		try {java.lang.Thread.sleep(8000);} catch (InterruptedException e) {}
		
		System.out.println("\n-------------------------------------------------" +
				"----------------------------");
		System.out.println("Listen to UDP Multicast broadcast for 10 seconds " +
				"with delay of half a second:");
		MulticastListener multicastListener3 = new MulticastListener("10 seconds broadcast with delay listener");
		multicastListener3.setTimer(10000);
		multicastListener3.setDelay(500);
		multicastListener3.setInfoMessages(true);
		multicastListener3.start();
	}	
}

class MulticastListener extends Thread {
	public MulticastListener(String threadName) {
		super(threadName);
	}
	
	public void execute () {
		try {
			System.out.println("Running MulticastListener execute()");
			// Create UDP socket and join a group to listen broadcasted messages
	    	MulticastSocket multicastSocket = new MulticastSocket(4447);
	        InetAddress groupAddress = InetAddress.getByName("230.0.0.1");
	        multicastSocket.joinGroup(groupAddress); 
	        
	        // Create datagram packet to receive data from a server
	        DatagramPacket packet;
		    byte[] buffer = new byte[256];
	        packet = new DatagramPacket(buffer, buffer.length);
	    
	        // Do not wait forever on .receive(packet) method but time
	        // out after 1 second
	        multicastSocket.setSoTimeout(1000);
	        // Receive data from (listen to) a server
	        multicastSocket.receive(packet);
	        // Note: the above method will wait until any server broadcasts a message 
	        // to "230.0.0.1" group, port 4447

	        // Convert (decode) packet data into text (String)
	        String received = new String(packet.getData(), 0, packet.getLength());
	        System.out.println("UDP server told me: " + received);
	        InetAddress serverAddress = packet.getAddress();
	        System.out.println("UDP server sent a message from IP: " + serverAddress);

	        // Close all UDP resources
	        multicastSocket.leaveGroup(groupAddress);
	        multicastSocket.close();			
		} catch (IOException ioe) {	
			System.out.println("multicastSocket.receive(packet) timed out");
		}
	
	}
}

