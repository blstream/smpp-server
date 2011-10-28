package com.blstream.smppserver.util;

import ie.omk.smpp.net.StreamLink;

import java.io.IOException;
import java.net.Socket;

/**
 * @author German Escobar
 * 
 * THIS FILE HAS BEEN CHANGED IN RELATION TO THE ORIGINAL VERSION BY
 * @author Przemyslaw Pokrywka
 * 
 */
public class SocketLink extends StreamLink {
	
	private Socket socket;

	public SocketLink(Socket socket) throws IOException {
		super(socket.getInputStream(), socket.getOutputStream());
		
		this.socket = socket;
	}

	@Override
	public void implClose() {
		super.implClose();
		
		try { socket.close(); } catch (Exception e) {}
	}

}
