package contoCorrente.server.comando;

public class Bonifico extends Comando {

	private int idContoPrelievo;
	private String codiceClientePrelievo;
	private int idContoVersamento;
	private double quantita;
	
	public Bonifico(int idContoPrelievo, String codiceClientePrelievo, 
					int idContoVersamento, double quantita) {
		super();
		this.idContoPrelievo = idContoPrelievo;
		this.codiceClientePrelievo = codiceClientePrelievo;
		this.idContoVersamento = idContoVersamento;
		this.quantita = quantita;
	}


	@Override
	public int esegui() {
		if(gestore.bonifico(idContoPrelievo, codiceClientePrelievo, 
							idContoVersamento, quantita))
			return 1;
		
		return 0;
	}

}
