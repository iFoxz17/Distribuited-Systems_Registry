package contoCorrente.server;

import java.util.HashSet;
import java.util.Set;

import contoCorrente.Header;
import contoCorrente.server.comando.*;

public class CommandParser {
	
	static Comando parseRequest(String command) {
		
		String[] request = command.split(Header.SEP); 
		Comando com = null;
		int idConto;
		double quantita;
		String cliente;
		
		switch(request[0]) {
			case Header.NUOVO_CONTO:
				if(request.length>1) {
					Set<String> intestatari = new HashSet<>();
					for(int i=1;i<request.length;i++)
						intestatari.add(request[i]);
					com = new CreazioneConto(intestatari);
				}
				break;
				
			case Header.VERSA:
				try {
					idConto = Integer.parseInt(request[1]);
					cliente = request[2];
					quantita = Double.parseDouble(request[3]);
					com = new Versamento(idConto, cliente, quantita);
				}
				catch(Exception e) {}
				break;
				
			case Header.PRELEVA:
				try {
					idConto = Integer.parseInt(request[1]);
					cliente = request[2];
					quantita = Double.parseDouble(request[3]);
					com = new Prelievo(idConto, cliente, quantita);
				}
				catch(Exception e) {}
				break;
				
			case Header.BONIFICO:
				int idContoVersamento;
				try {
					idConto = Integer.parseInt(request[1]);
					cliente = request[2];
					idContoVersamento = Integer.parseInt(request[3]);
					quantita = Double.parseDouble(request[4]);
					com = new Bonifico(idConto, cliente, idContoVersamento, quantita);
				}
				catch(Exception e) {}
		}
		
		return com;
	}
	
}
