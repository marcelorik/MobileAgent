package ufu.sd.mobilePlatform;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*; 
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ufu.sd.examples.jkp.launcher.JkpAgent;
 
/** Implementação de uma Agência Lançadora.
 */
public class Launcher 
	extends UnicastRemoteObject 
    implements ILauncher {
	
	/**Fila de Agentes que estão esperando para serem lançados.
	 */
	private BlockingQueue<IAgent> agentQueue = new LinkedBlockingQueue<IAgent>();
	
	/**Construtor. Inicia uma thread que controla a fila de Agentes
	 * que aguardam para serem lançados.
	 */
	public Launcher() throws RemoteException {
		super();
		
		// Thread que controla a fila de Agentes
		new Thread() {
	    	public void run() {	    		
	    		while(true) {
	    			IAgent agent = null;
	    			
	    			// Retira próximo agente da fila.
					try {
						agent = agentQueue.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	    			
					// Pede que Agente reporte.
	    			if (agent != null) {
	    				System.out.println("Agent "+agent.getName()+" has arrived back.");
	    				agent.report();
	    			}
	    		}
	    	}
	    }.start();
	}

	private IMonitor monitor;
  
	protected void setMonitor(IMonitor monitor) {
		this.monitor = monitor;		
	}
	
	public void launch(IAgent agent) throws RemoteException {
		if (monitor == null) {
			System.out.println("Cant't launch, monitor is not set.");
			return;
		}
		
		System.out.println("Launching agent \""+agent.getName()+"\".");
		
		ArrayList<String> services = agent.servicesWanted();
		
		Queue<IAgency> agencies = monitor.getAgenciesByServices(services);
		
		agent.setDestinations(agencies);
	}
	
	public void receiveBack(IAgent agent) throws RemoteException {
		agentQueue.add(agent);
	}        
}