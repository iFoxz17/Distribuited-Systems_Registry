package contoCorrente.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import contoCorrente.Header;
import registry.RegHeader;

public class Client {
	
	private static final int PAUSE = 500;
	private static final int MIN_OPERATION = 5;
	
	private static final String[] intestatari = new String[] 
			{"Andrea", "Giorgia"};
	
	private static String[] getServiceInfo(String registryIP, int registryPort) {
		Socket client = null;
		String res = null;
		
		BufferedReader input = null;
		PrintStream output = null;
		
		try {
		
			client = new Socket(registryIP, registryPort);
			
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new PrintStream(client.getOutputStream(), true);
			
			output.println(RegHeader.OP_LOOKUP + RegHeader.SEP + Header.SERVICE_NAME);
			output.flush();
								
			res = input.readLine();
	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		String[] fields = res.split(RegHeader.SEP);
		
		try {
			input.close();
			output.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(fields.length == 3)
			return fields;
		
		return null;
	}
	
	private static int createConto(BufferedReader input,
								   PrintStream output) {
		int nConto;
		
		String message = Header.NUOVO_CONTO;
		int rand = (int) (Math.random() * intestatari.length) + 1;
		
		for(int i=0;i<rand;i++)
			message = message + Header.SEP + intestatari[(int) (Math.random() * intestatari.length)];
		
		output.println(message);
		output.flush();
		
		try {
			message = input.readLine();
			nConto = Integer.parseInt(message);
		} catch (Exception e) {
			nConto = -1;
		}
		
		return nConto;
	}
	
	public static void main(String args[]) {
		
		System.out.println("Client: Retrieving service IP and port from registry");
		String[] serviceInfo = getServiceInfo(RegHeader.REGISTRY_IP, RegHeader.REGISTRY_PORT);
		
		if(serviceInfo == null) {
			System.out.println("Fail retrieving service IP");
			System.exit(0);
		}
		
		Socket client = null;
		BufferedReader input = null;
		PrintStream output = null;
		int operationCont = 0;
		String op;
		
		OperationBuffer buff = new OperationBuffer();
		
		try {
			
			System.out.println("Client: connecting to service");
			client = new Socket(serviceInfo[0], Integer.parseInt(serviceInfo[1]));
			
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new PrintStream(client.getOutputStream(), true);
		
			System.out.println("Client: creating new bill");
			int codiceConto = createConto(input, output);
			
			System.out.println("Client: creating request factory");
			OperationFactory fact = new OperationFactory(buff, codiceConto, 
												intestatari);
			fact.start();
			
			while(operationCont < MIN_OPERATION) {
				
				op = buff.get();
				
				System.out.println("Client: sending request " + (operationCont+1));
				
				output.println(op);
				output.flush();
				
				op = input.readLine();
				if(op.equals(Header.OP_SUCC))
					System.out.println("Client: request " + (operationCont+1) + " successful");
				else
					System.out.println("Client: request " + (operationCont+1) + " unsuccessful");
				
				operationCont++;
				Thread.sleep(PAUSE);
			}
			
		}
		catch(Exception e) {
			System.out.println("Exception during request " + (operationCont+1));
			e.printStackTrace();
		}
		
		
		System.out.println("Client: closing connection");
		
		output.println();
		output.flush();
		
		
		try {
			input.close();
			client.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Client: terminating");
		
	}

}
