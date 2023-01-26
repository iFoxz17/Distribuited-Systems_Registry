package contoCorrente.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import contoCorrente.Header;

public class Server {
	
	private ServerSocket server;
	
	Server() {
		
		try {
			server = new ServerSocket(Header.SERVICE_PORT);
		} catch (IOException e) {
			System.out.println("Errore nella creazione del server");
			System.exit(0);
		}
	}
	
	void loop() {
		
		Operazione op;
		
		while(true) {
			try {
				System.out.println("Service " + Header.SERVICE_NAME + ": " + 
								   "waiting for client to connect");
				
				Socket client = server.accept();
				
				System.out.println("Service " + Header.SERVICE_NAME + ": " + 
						   "client " + client.getInetAddress() + " accepted");
				
				op = new Operazione(client);
				op.start();
				
				System.out.println("Service " + Header.SERVICE_NAME + ": " + 
						   "new thread started for client " + client.getInetAddress());
			}
			
			catch(Exception e) {
				System.out.println("Error in client acceptance");
				e.printStackTrace();
			}
			
		}
	}
}
