package com.blstream.smppserver;

import ie.omk.smpp.message.SMPPPacket;

import java.net.Socket;
import java.util.Date;

import ie.omk.smpp.message.SMPPResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * @author German Escobar
 * 
 * THIS FILE HAS BEEN CHANGED IN RELATION TO THE ORIGINAL VERSION BY
 * @author Przemyslaw Pokrywka
 * 
 */
public class SmppServerTest {
	
	private final int PORT = 4444;
	
	@Test
	public void shouldAcceptConnection() throws Exception {
		
		// start the SMPP Server
		SmppServer smppServer = new SmppServer(PORT);
		smppServer.start();
		
		// set a custom packet processor
		PacketProcessor packetProcessor = new PacketProcessor() {

			@Override
			public Response processPacket(SMPPPacket packet, SMPPResponse response, SmppSession.SendResponseAction sendResponseBackAction) {
				return Response.OK;
			}
			
		};
		smppServer.setPacketProcessor(packetProcessor);
		
		new Socket("localhost", PORT);
		
		assertSessionsCreated(smppServer, 1, 500);
		
		SmppSession session = smppServer.getSessions().iterator().next();
		Assert.assertNotNull(session);
		Assert.assertEquals(session.getPacketProcessor(), packetProcessor);
		
		stopServer(smppServer, 1000);
		
	}
	
	@Test
	public void shouldAcceptMultipleConnections() throws Exception {
		
		// start the SMPP Server
		SmppServer smppServer = new SmppServer(PORT);
		smppServer.start();
		
		for (int i=0; i < 200; i++) {
			new Socket("localhost", PORT);
		}
		
		assertSessionsCreated(smppServer, 200, 5000);
		
		stopServer(smppServer, 1000);
		
	}
	
	private void assertSessionsCreated(SmppServer smppServer, int numSessions, long timeout) {
		
		boolean asserted = false;
		long startTime = new Date().getTime();
		
		while (!asserted && (new Date().getTime() - startTime) < timeout) {
			if (smppServer.getSessions().size() == numSessions) {
				asserted = true;
			} else {
				try { Thread.sleep(200); } catch (InterruptedException e) {}
			}
			
		}
		
		Assert.assertEquals(smppServer.getSessions().size(), numSessions);
	}
	
	private void stopServer(SmppServer server, long timeout) {
		
		server.stop();
		
		boolean stopped = false;
		long startTime = new Date().getTime();
		
		while (!stopped && (new Date().getTime() - startTime) < timeout) {
			if (server.getStatus().equals(SmppServer.Status.STOPPED)) {
				stopped = true;
			} else {
				try { Thread.sleep(200); } catch (InterruptedException e) {}
			}
		
		}
		
		Assert.assertEquals(server.getStatus(), SmppServer.Status.STOPPED);
	}
	
}
