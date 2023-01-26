package contoCorrente.server;

import java.util.Set;

public class ContoCorrente {
	
	private Set<String> codiceCliente;
	private int codiceConto;
	private double ammontare;
	
	
	ContoCorrente(Set<String> codiceCliente, int codiceConto) {
		this.codiceCliente = codiceCliente;
		this.codiceConto = codiceConto;
		this.ammontare = 0;
	}
	
	
	int getCodiceConto() {
		return codiceConto;
	}

	synchronized double getAmmontare() {
		return ammontare;
	}
	
	boolean cercaIntestatario(String codiceCliente) {
		return this.codiceCliente.contains(codiceCliente);
	}
	
	synchronized boolean preleva(double quantita) {
		if(ammontare>=quantita) {
			ammontare -= quantita;
			return true;
		}
		
		return false;
	}
	
	synchronized boolean versa(double quantita) {
		ammontare += quantita;
		return true;
	}
	
}
