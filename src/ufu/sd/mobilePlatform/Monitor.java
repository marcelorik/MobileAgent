package ufu.sd.mobilePlatform;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*; 
import java.util.*;

 
/**Implementação de um Monitor de Agências remotas.
 */
public class Monitor 
	extends UnicastRemoteObject 
    implements IMonitor {
  
    /** Lista das agências que ele toma conta.
     */
    private ArrayList<IAgency> agencies = new ArrayList<IAgency>();
    private String adress;
	
    
    /** Lock utilizado para acesso concorrente à
     * lista de Agências.
     */
    private Object agenciesLock = new Object();
    
    public ArrayList<IAgency> getAgencies() {
    	synchronized(agenciesLock) {
    		return agencies;
    	}
	}
    
	
	public Monitor() throws RemoteException {
      super();
    }
	
	public Queue<IAgency> getAgenciesByServices(ArrayList<String> services) throws RemoteException {
		Queue<IAgency> queue = new LinkedList<IAgency>();
		
		synchronized(agenciesLock) {
			for (int i=0; i < agencies.size(); i++) {
				try {
					IAgency agency = agencies.get(i);
					boolean hasAll = true;
					for (String wanted : services) {
						
							if (!agency.hasService(wanted)) {
								hasAll = false;
								break;
							}
					}
					if (hasAll)
						queue.add(agency);
				} catch (RemoteException e) {
					System.out.println("One agency is down.");
					this.agencies.remove(i);
				}
			}
		}
		
		return queue;
	}
	
	public void registerAgency(IAgency agency) throws RemoteException {		
		synchronized(agenciesLock) {
			agencies.add(agency);
		}
		System.out.println("Agency added : "+agency.getName());	
    }
	
	public boolean unregisterAgency(IAgency agency) {
		synchronized(agenciesLock) {
			return agencies.remove(agency);
		}
	}        
}