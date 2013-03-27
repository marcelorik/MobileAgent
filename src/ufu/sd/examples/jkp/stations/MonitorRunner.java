package ufu.sd.examples.jkp.stations;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;

import ufu.sd.mobilePlatform.IMonitor;
import ufu.sd.mobilePlatform.Monitor;
import ufu.sd.mobilePlatform.Utility;

public class MonitorRunner extends Monitor {

	public MonitorRunner() throws RemoteException {
		super();
	}

	public static void main(String args[])  throws RemoteException {
		System.out.println("Starting Monitor...");		
		
		HashMap<String,String> argMap = Utility.parseArgs(args);
		
		Utility.installSecurityManager();
		Utility.installRegistry();
		
		MonitorRunner monitor = new MonitorRunner();
		
		String address;
		if (argMap.containsKey("address") && argMap.get("address") != null)
			address = argMap.get("address");
		else
			address = "//localhost/";
		
		try {
			Naming.rebind(address+"monitor", monitor);
		 } catch (Exception e) {
            System.err.println("RMI server exception:" + e);
            e.printStackTrace();
		 }	
		System.out.println("\nMonitor is ready.");		
		
    }
}
