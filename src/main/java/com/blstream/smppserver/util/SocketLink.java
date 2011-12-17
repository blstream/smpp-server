package com.blstream.smppserver.util;

import ie.omk.smpp.net.StreamLink;

import java.io.IOException;
import java.net.Socket;

/**
 * Copyright 2011 German Escobar
 * Copyright 2011 BLStream
 * Copyright 2011 Przemysław Pokrywka <przemyslaw.pokrywka@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 
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
