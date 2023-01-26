package contoCorrente.server.comando;

import contoCorrente.server.GestoreContoCorrente;
import contoCorrente.server.GestoreContoCorrenteFactory;

public abstract class Comando {
	
	protected GestoreContoCorrente gestore;
	
	public Comando() {
		gestore = GestoreContoCorrenteFactory.getInstance();
	}
	
	public abstract int esegui();
	
}
