package registry;

public final class RegHeader {
	
	public static final String REGISTRY_IP = "localhost";
	public static final int REGISTRY_PORT = 56432;

	public static final String OP_BIND = "0";
	public static final String OP_UNBIND = "1";
	public static final String OP_REBIND = "2";
	public static final String OP_LOOKUP = "3";
	
	public static final String SEP = "#";
	
	public static final String OP_FAIL = "0";
	public static final String OP_SUCC = "1";
	
}
