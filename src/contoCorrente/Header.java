package contoCorrente;

public final class Header {
	
	public final static String SERVICE_NAME = "ContoCorrenteMiaBanca";
	public final static String SERVICE_IP = "localhost";
	public final static int SERVICE_PORT = 3013;
	public final static String SERVICE_PROTOCOL = "ContoCorrenteProtocol";
	
	public final static String SEP = "#";
	
	public final static String NUOVO_CONTO = "N";
	public final static String VERSA = "V";
	public final static String PRELEVA = "P";
	public final static String BONIFICO = "B";
	
	public static final String OP_FAIL = "0";
	public static final String OP_SUCC = "1";
}
