package registry;

public class RegistryLauncher {
	
	public static void main(String args[]) {
		
		System.out.println("RegistryLauncher: creating new registry");
		Registry reg = new Registry();
		
		System.out.println("RegistryLauncher: registry listening at " + RegHeader.REGISTRY_IP + 
				":" + RegHeader.REGISTRY_PORT);
		
		reg.loop();
		System.out.println("RegistryLauncher: registry ended");
		
	}
	
}
