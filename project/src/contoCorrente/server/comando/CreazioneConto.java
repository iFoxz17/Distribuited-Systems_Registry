package contoCorrente.server.comando;

import java.util.Set;

public class CreazioneConto extends Comando {

	private Set<String> codiceCliente;
	
	
	public CreazioneConto(Set<String> codiceCliente) {
		super();
		this.codiceCliente = codiceCliente;
	}

	@Override
	public int esegui() {
		return gestore.nuovoConto(codiceCliente);
	}

}
