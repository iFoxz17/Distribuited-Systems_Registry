package contoCorrente.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import contoCorrente.server.comando.Comando;

public class Operazione extends Thread {

	private Socket client;
	
	public Operazione(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		
		BufferedReader input = null;
		PrintStream output = null;
		
		int result = -1;
		Comando comando;
		String request;
		
		try {
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new PrintStream(client.getOutputStream(), true);
			
			do {
				
				System.out.println("Thread " + Thread.currentThread().getId() + 
								   ": " + "waiting for request");
				request = input.readLine();
				
				System.out.println("Thread " + Thread.currentThread().getId() + 
						   		   ": " + "Parsing request: " + request);
				comando = CommandParser.parseRequest(request);
				
				if(comando!=null) {
					System.out.println("Thread " + Thread.currentThread().getId() + 
							   		   ": " + "Executing request");
					result = comando.esegui();
				}
				
				output.println(result);
				output.flush();
			
			} while(comando!=null);
			
			System.out.println("Thread " + Thread.currentThread().getId() + 
					   		   ": " + "Terminating thread" + request);
			input.close();
			output.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
