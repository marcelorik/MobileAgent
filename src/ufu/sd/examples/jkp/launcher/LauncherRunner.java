package ufu.sd.examples.jkp.launcher;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

import ufu.sd.mobilePlatform.IAgent;
import ufu.sd.mobilePlatform.ILauncher;
import ufu.sd.mobilePlatform.IMonitor;
import ufu.sd.mobilePlatform.Launcher;
import ufu.sd.mobilePlatform.Utility;

public class LauncherRunner extends Launcher {
	public LauncherRunner() throws RemoteException {
		super();
	}

	public static void main(String args[])  throws RemoteException, MalformedURLException {
        
		System.out.println("Starting launcher...");		
		
		HashMap<String,String> argMap = Utility.parseArgs(args);
		
		LauncherRunner launcher = new LauncherRunner();
				
		String monitorAddress;
		if (argMap.containsKey("monitorAddress") && argMap.get("monitorAddress") != null)
			monitorAddress = argMap.get("monitorAddress");
		else
			monitorAddress = "//localhost/";
		
		try {
			launcher.setMonitor((IMonitor) Naming.lookup(monitorAddress+"monitor"));
		} catch (ConnectException  e) {
			System.out.println("Could not connect to Monitor.");
		} catch (NotBoundException  e) {
			System.out.println("Could not find to Monitor.");
		}
		
		
		Utility.installSecurityManager();
		Utility.installRegistry();
		
		System.out.println("\nLauncher is ready.");		
		
		launcher.launch((IAgent) new JkpAgent("Jason Bourne",launcher));

    }
}
