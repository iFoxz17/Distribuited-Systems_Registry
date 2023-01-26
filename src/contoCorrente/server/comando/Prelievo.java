package contoCorrente.server.comando;

public class Prelievo extends Comando {
	
	private int idConto;
	private String codiceCliente;
	private double quantita;
	
	public Prelievo(int idConto, String codiceCliente, double quantita) {
		super();
		this.idConto = idConto;
		this.codiceCliente = codiceCliente;
		this.quantita = quantita;
	}

	@Override
	public int esegui() {
		if(gestore.preleva(idConto, codiceCliente, quantita))
			return 1;
		
		return 0;
	}
}
