package contoCorrente.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

import contoCorrente.Header;
import registry.RegHeader;

public class ContoCorrenteRegister {
	
	public static void main(String args[]) {
			
		Socket client;
		BufferedReader input = null;
		PrintStream output = null;
		
		try {
			
			System.out.println("ContoCorrenteRegister: connecting to registry " +
							   RegHeader.REGISTRY_IP + ":" + RegHeader.REGISTRY_PORT);
																					
			
			client = new Socket(RegHeader.REGISTRY_IP, RegHeader.REGISTRY_PORT);
			
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new PrintStream(client.getOutputStream(), true);
			
			String ip = InetAddress.getByName(Header.SERVICE_IP).toString();
			int index = ip.indexOf('/'); 
			if(index!=-1)
				ip = ip.substring(index + 1);
					
			
			String request = RegHeader.OP_BIND + RegHeader.SEP + Header.SERVICE_NAME + 
					   		 RegHeader.SEP + ip +
					   		 RegHeader.SEP + Header.SERVICE_PORT + RegHeader.SEP + 
					   		 Header.SERVICE_PROTOCOL;
			
			System.out.println("ContoCorrenteRegister: sending registration request"
					+ " to registry");
			
			output.println(request);
			output.flush();
			
			String res = input.readLine();
			
			if(res.equals(RegHeader.OP_SUCC))
				System.out.println("ContoCorrenteRegister: service registrated successfully");
			else
				System.out.println("ContoCorrenteRegister: service registration failed");
			
			
		} catch (Exception e) {
			System.out.println("Exception during service registration");
			e.printStackTrace();
		}
		
		System.out.println("ContoCorrenteRegister: terminating");
			
	}
	
}
