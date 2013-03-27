package ufu.sd.examples.jkp.stations;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Random;

import ufu.sd.mobilePlatform.Agency;
import ufu.sd.mobilePlatform.IAgency;
import ufu.sd.mobilePlatform.IMonitor;
import ufu.sd.mobilePlatform.IService;
import ufu.sd.mobilePlatform.Utility;

public class JkpAgencyRunner extends Agency {

	public JkpAgencyRunner(String name) throws RemoteException {
		super(name);
	}

	public static void main(String args[]) throws RemoteException {
        
		System.out.println("Starting agency...");
		
		Utility.installSecurityManager();
		Utility.installRegistry();
		
		HashMap<String,String> argMap = Utility.parseArgs(args);
		
		try {   
			
			String name;
			if (argMap.containsKey("name") && argMap.get("name") != null)
				name = argMap.get("name");
			else
				name = "AG"+((new Random()).nextInt(9999));
			JkpAgencyRunner agency = new JkpAgencyRunner(name);
			
			String address;
			if (argMap.containsKey("address") && argMap.get("address") != null)
				address = argMap.get("address");
			else
				address = "//localhost/";
			
			IService jkpservice = new JkpService();
			agency.addService(jkpservice);
			
			Naming.rebind(address+agency.getName(), agency);
			
			try {
				String monitorAddress;
				if (argMap.containsKey("monitorAddress") && argMap.get("monitorAddress") != null)
					monitorAddress = argMap.get("monitorAddress");
				else
					monitorAddress = "//localhost/";
				IMonitor monitor = (IMonitor) Naming.lookup(monitorAddress+"monitor");
				monitor.registerAgency((IAgency) agency);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Could not reach Monitor. Agency "+address+"/"+agency.getName()+" is lost.");
			}
				
			System.out.println("\n"+agency.getName()+" is ready.");				
			
       } catch (Exception e) {
            System.err.println("RMI server exception:" + e);
            e.printStackTrace();
       }
   }
}
