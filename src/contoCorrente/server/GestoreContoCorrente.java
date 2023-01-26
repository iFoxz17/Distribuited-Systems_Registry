package contoCorrente.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GestoreContoCorrente {
	
	private Map<Integer, ContoCorrente> conti;
	private static int nConto;
	
	
	GestoreContoCorrente() {
		conti = new HashMap<>();
		nConto = 1;
	}
	
	public int nuovoConto(Set<String> codiceCliente) {
		
		Set<String> clienti = new HashSet<String>();
		for(String s:codiceCliente)
			clienti.add(s);
		
		ContoCorrente c;
		
		synchronized(this) {
			c = new ContoCorrente(clienti, nConto);
			conti.put(nConto, c);
			nConto++;
		}
		
		return c.getCodiceConto();
		
	}
	
	public double getAmmontare(int idConto, String codiceClientePrelievo) {
		ContoCorrente c = conti.get(idConto);
				
		if(c==null)
			return -1;
		
		if(!c.cercaIntestatario(codiceClientePrelievo))
			return -1;
		
		return c.getAmmontare();
	
	}
	
	public boolean versa(int idConto, String codiceClientePrelievo, double quantita) {
		
		ContoCorrente c = conti.get(idConto);
		
		if(c==null)
			return false;
		
		if(!c.cercaIntestatario(codiceClientePrelievo))
			return false;
		
		return c.versa(quantita);
	}
	
	public boolean preleva(int idConto, String codiceClientePrelievo, double quantita) {
			
			ContoCorrente c = conti.get(idConto);
			
			if(c==null)
				return false;
			
			if(!c.cercaIntestatario(codiceClientePrelievo))
				return false;
			
			return c.preleva(quantita);
		}
	
	public boolean bonifico(int idContoPrelievo, String codiceClientePrelievo, 
					 int idContoVersamento, double quantita) {
			
			ContoCorrente prelievo = conti.get(idContoPrelievo);
			if(prelievo==null)
				return false;
			
			if(!prelievo.cercaIntestatario(codiceClientePrelievo))
				return false;
			
			ContoCorrente versamento = conti.get(idContoVersamento);
			if(versamento==null)
				return false;
			
			if(prelievo.preleva(quantita))
				if(versamento.versa(quantita))
					return true;
				else 
					prelievo.versa(quantita);
			
			return false;
		}
}
