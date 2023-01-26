package registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Registry {

		private ServerSocket server;
		private Map<String, ServiceDescription> services;
		
		
		public Registry() {
			try {
				server = new ServerSocket(RegHeader.REGISTRY_PORT);
			} catch (IOException e) {
				System.out.println("Exception during registry creation");
				e.printStackTrace();
			}
			
			services = new HashMap<>();
		}
		
		
		private boolean checkIPV4Address(String address) {
			/*
			if(address.length()>0 && address.charAt(0) == '/')
				address = address.substring(1);
			
			String[] fields = address.split(".");
			if(fields.length!=4)
				return false;
			
			int field;
			
			for(int i=0;i<fields.length;i++) {
				field = Integer.parseInt(fields[i]);
				if(field<0 || field>255)
					return false;
			}
			*/
			return true;
		}
		
		private boolean checkPort(int port) {
					return port>=0 && port<65536;
		}
		
		
		private boolean bind(String nomeServizio, String serverIP, 
							int porta, String protocollo) {
			
			if(services.containsKey(nomeServizio.toUpperCase()))
				return false;
			
			if(!checkIPV4Address(serverIP))
				return false;
			
			if(!checkPort(porta))
				return false;
				
			ServiceDescription desc = new ServiceDescription(nomeServizio, serverIP, 
									   porta, protocollo);
			
			services.put(nomeServizio.toUpperCase(), desc);
			return true;
		}
		
		private boolean unbind(String nomeServizio, String serverIP, 
				int porta) {
			
			ServiceDescription desc = services.get(nomeServizio.toUpperCase());
			
			if(desc==null)
				return false;
			
			if(desc.getName().equalsIgnoreCase(nomeServizio) &&
			   desc.getIPAddress().equalsIgnoreCase(serverIP) &&
			   desc.getPort() == porta) {
				services.remove(nomeServizio);
				return true;
			}
			
			
			return false;
			
		}
		
		private boolean rebind(String nomeServizio, String serverIP, 
				int porta, String protocollo) {
			
			if(unbind(nomeServizio, serverIP, porta)) {
				return bind(nomeServizio, serverIP, porta, protocollo);
			}
			
			return false;
		}
		
		private String lookup(String nomeServizio) {
			
			ServiceDescription desc = services.get(nomeServizio.toUpperCase());
			if(desc == null)
				return null;
			
			String reference = desc.getIPAddress();
			reference = reference + RegHeader.SEP + desc.getPort();
			reference = reference + RegHeader.SEP + desc.getProtocol();
			
			return reference;
		}
		
		
		public void loop() {
			
			String message = null;
			Socket socket;
			BufferedReader input = null;
			PrintStream output = null;
			
			while(true) {
				
				try {
					
					System.out.println("Registry: waiting for client connection");
					socket = server.accept();
					input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					output = new PrintStream(socket.getOutputStream(), true);
					
					System.out.println("Registry: client connected, "
							+ "waiting for client request");
					
					message = input.readLine();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				System.out.println("Registry: parsing request");
				Request request = Request.parse(message);
				String result = "";
				
				if(request==null) {
					System.out.println("Registry: error during request parse");
					result = RegHeader.OP_FAIL;
				}
				else {
					System.out.println("Registry: serving request");
					switch(request.getOperation()) {
						case RegHeader.OP_BIND:
							result = bind(request.getServiceName(), request.getIpAddress(),
										  request.getPort(), request.getProtocol()) ?
										  RegHeader.OP_SUCC : RegHeader.OP_FAIL;
							break;
						case RegHeader.OP_UNBIND:
							result = unbind(request.getServiceName(), request.getIpAddress(),
									  request.getPort()) ?
									  RegHeader.OP_SUCC : RegHeader.OP_FAIL;
							break;
						case RegHeader.OP_REBIND:
							result = rebind(request.getServiceName(), request.getIpAddress(),
									  request.getPort(), request.getProtocol()) ?
									  RegHeader.OP_SUCC : RegHeader.OP_FAIL;
							break;
						case RegHeader.OP_LOOKUP:
							result = lookup(request.getServiceName());
							if(result == null)
								result = RegHeader.OP_FAIL;
					}
				}
				
				System.out.println("Registry: sending result");
				output.println(result);
				output.flush();
					
			}
			
		}
		
}
