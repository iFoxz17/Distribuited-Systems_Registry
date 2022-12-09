package registry;

public class ServiceDescription {
	
	private String name;
	private String ipAddress;
	private int port;
	private String protocol;
	
	public ServiceDescription(String name,String ipAddress, int port, String protocol) {
		super();
		this.name = name;
		this.ipAddress = ipAddress;
		this.port = port;
		this.protocol = protocol;
	}
	

	public String getName() {
		return name;
	}

	public String getIPAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}
}
