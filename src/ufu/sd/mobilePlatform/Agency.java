package ufu.sd.mobilePlatform;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ufu.sd.examples.jkp.stations.JkpService;
import ufu.sd.mobilePlatform.exception.ServiceClashException;
import ufu.sd.mobilePlatform.exception.UnavailableServiceException;
 
/** Implementação de uma agência que recebe agentes móveis
 * e oferece serviços.
 */
public class Agency
    extends UnicastRemoteObject 
    implements IAgency {
    
    /** Lista de serviços que a agência oferece aos agentes.
     */
    private ArrayList<IService> services = new ArrayList<IService>();
    
    private String name;
    private String adress;
    
    
    /** Fila de agentes que estão esperando para serem executados.
     */
    private BlockingQueue<IAgent> agentQueue = new LinkedBlockingQueue<IAgent>();
    
    /** Lock para proteger a variável services de acessos concorrentes.
     */
    private Object servicesLock = new Object();
    
	public String getName() {
		return this.name;
	}
	
    /** Construtor da Agência. Inicia a thread que cuida da fila
     * de agentes que serão executados.
     * @param name Nome da Agência
     */
    public Agency(String name) throws RemoteException {
      super();
      this.name = name;
      
      	// Thread que cuida da fila:
	    new Thread() {
	    	public void run() {
	    		// Pega referência da classe pai.
	    		Agency agency = Agency.this;
	    		
	    		while(true) {
	    			IAgent agent = null;
					try {
						// Pega próximo da fila:
						agent = agentQueue.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	    			
					//Executa o agente
	    			if (agent != null) {
	    				System.out.println("Executing agent "+agent.getName());
						agent.execute(agency);
	    			}
	    		}
	    	}
	    }.start();
    
    }
	
    
	public void addService(IService service) throws ServiceClashException {
		//Não deixa adicionar um serviço com o mesmo nome
		synchronized(servicesLock) {
			for (IService s : services)
				if (service.getName() == s.getName())
					throw new ServiceClashException(service.getName());
			
			services.add(service);
		}
    }
	
	public void receiveAgent(IAgent agent) throws RemoteException { 
			System.out.println("Agent received: "+agent.getName());
			agentQueue.add(agent);			
    }
	
	public boolean isAlive() throws RemoteException { 
		return true;
    }
   
    public ArrayList<String> availableServices() throws RemoteException {
    	ArrayList<String> serviceNames = new ArrayList<String>();
    	
    	synchronized(servicesLock) {
	    	for (IService s : services)
	    		serviceNames.add(s.getName());
    	}
    	
		return serviceNames;	
    }
	
    public Object runService(String name, Object[] args) 
    	throws RemoteException, UnavailableServiceException, IllegalArgumentException, ClassCastException {
    	
    	IService service = null;
    	
    	synchronized(servicesLock) {
	    	for (IService s : services) {
	    		if (s.getName() == name)
	    			service = s;
	    	}
    	}
    	
    	if (service == null)
    		throw new UnavailableServiceException(name);
    	
    	return service.run(args);
    }
    
    public boolean hasService(String wanted) {
    	
    	synchronized(servicesLock) {
			for (IService service : services) 
				if (service.getName().equals(wanted))
					return true;
    	}
		return false;
	}
}
