package contoCorrente.server;

import contoCorrente.Header;

public class ServerLauncher {
	
	public static void main(String args[]) {
		
		System.out.println("ServerLauncher: Creating new server");
		Server server = new Server();
		System.out.println("ServerLauncher: "
				+ "Created new server listening on port " + Header.SERVICE_PORT);
		server.loop();
		System.out.println("ServerLauncher: Server ended execution");
	}

}
