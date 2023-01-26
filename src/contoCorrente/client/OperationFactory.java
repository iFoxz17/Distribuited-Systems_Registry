package contoCorrente.client;

import contoCorrente.Header;

public class OperationFactory extends Thread {
	
	private static final int VARIANCE = 100;
	private static final int DEFAULT_CONT = 5;
	
	private int cont;
	private OperationBuffer buffer;
	private String[] codiceCliente;
	private int codiceConto;
	
	public OperationFactory(OperationBuffer buff, int codiceConto, String[] codiceCliente, 
							int cont) {
		this.cont = cont;
		this.buffer = buff;
		this.codiceCliente = codiceCliente;
		this.codiceConto = codiceConto;
	}
	
	public OperationFactory(OperationBuffer buff, int codiceConto, String[] codiceCliente) {
		this(buff, codiceConto, codiceCliente, DEFAULT_CONT);
	}
	
	private String buildCommand(String type) {
		
		String operation = type + Header.SEP + 
						   codiceConto + Header.SEP + codiceCliente[(int) (Math.random()*codiceCliente.length)] + 
						   Header.SEP;
		
		return operation;
	}
	
	@Override
	public void run() {
		
		String operation;
		
		operation = buildCommand(Header.VERSA) + (int) (Math.random() * VARIANCE); 
		try {
			buffer.insert(operation);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Thread " + Thread.currentThread().getId() + 
				   ": added first payment to buffer");
		
		for(int i=0;i<cont;i++) {
			switch((int) (Math.random()*3)) {
			case 0: //Versamento
				operation = buildCommand(Header.VERSA) + (int) (Math.random() * VARIANCE); 
				try {
					buffer.insert(operation);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
				
			case 1: //Prelievo
				operation = buildCommand(Header.PRELEVA) + (int) (Math.random() * VARIANCE/2);
				try {
					buffer.insert(operation);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
				
			case 2: //Bonifico
				int codiceConto = (int) (Math.random() * 2) + 1;
				operation = buildCommand(Header.BONIFICO) + codiceConto + 
								Header.SEP + (int) (Math.random() * VARIANCE/2);
				
				try {
					buffer.insert(operation);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("Thread " + Thread.currentThread().getId() + 
							   ": added request " + (i+1) + "/" + cont + " to buffer");
		}
		
	}
	
}
