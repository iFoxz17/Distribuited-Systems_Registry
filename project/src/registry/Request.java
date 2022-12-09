package registry;

public class Request {
	
	private String operation;
	private String serviceName;
	private String ipAddress;
	private int port;
	private String protocol;
	
	Request(String operation, String serviceName, String ipAddress, 
				   int port, String protocol) {
		super();
		this.operation = operation;
		this.serviceName = serviceName;
		this.ipAddress = ipAddress;
		this.port = port;
		this.protocol = protocol;
	}
	
	
	
	String getOperation() {
		return operation;
	}

	String getServiceName() {
		return serviceName;
	}

	String getIpAddress() {
		return ipAddress;
	}

	int getPort() {
		return port;
	}

	String getProtocol() {
		return protocol;
	}



	public static Request parse(String request) {
		
		String[] fields = request.split(RegHeader.SEP);
		Request req = null;
		
		switch(fields[0]) {
		case RegHeader.OP_BIND: 
		case RegHeader.OP_REBIND:
			if(fields.length == 5) {
				int port;
				try {
					port = Integer.parseInt(fields[3]);
				}
				catch(Exception e) {
					return null;
				}
			
			req = new Request(fields[0], fields[1], fields[2], 
							  port, fields[4]);
			}
			break;
			
		case RegHeader.OP_UNBIND: 
			if(fields.length == 4) {
				int port;
				try {
					port = Integer.parseInt(fields[3]);
				}
				catch(Exception e) {
					return null;
				}
			
			req = new Request(fields[0], fields[1], fields[2], 
							  port, null);
			}
			break;
			
		case RegHeader.OP_LOOKUP: 
			if(fields.length == 2) {
				req = new Request(fields[0], fields[1], null, 
							  -1, null);
			}
			break;
		}
		
		return req;
	}
	
}
