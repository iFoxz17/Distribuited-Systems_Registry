package contoCorrente.server;

public class GestoreContoCorrenteFactory {
	
	private static GestoreContoCorrente gestore;
	
	public GestoreContoCorrenteFactory() {}
	
	public synchronized static GestoreContoCorrente getInstance() {
		if(gestore==null)
			gestore = new GestoreContoCorrente();
		
		return gestore;
	}
}
