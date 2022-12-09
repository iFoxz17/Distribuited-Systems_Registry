package contoCorrente.server.comando;

public class Versamento extends Comando {

	private int idConto;
	private String codiceCliente;
	private double quantita;
	
	public Versamento(int idConto, String codiceCliente, double quantita) {
		super();
		this.idConto = idConto;
		this.codiceCliente = codiceCliente;
		this.quantita = quantita;
	}

	@Override
	public int esegui() {
		if(gestore.versa(idConto, codiceCliente, quantita))
			return 1;
		
		return 0;
	}

}
