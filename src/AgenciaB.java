import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*; 
import java.util.*;
 
public class AgenciaB 
    extends UnicastRemoteObject 
    implements AgenciaI {
        
    //Map dos serviços que a agência possui
    private HashMap<Integer , List<ServiceI> > services = new HashMap<Integer , List<ServiceI> >();
	private static final String HOST = "//127.0.0.1:1099/";
    private String name;
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
    public Agencia() throws RemoteException {
      super();
    }
	
	/* 
		Adiciona serviços à lista de serviços da agência
	*/
	 public void addService(ServiceI service) throws RemoteException 
    { 
		if(!services.containsKey(service.typeService())){//Se não existir, cria-se a lista
			List<ServiceI> list = new ArrayList<ServiceI>();
			list.add(service);
			this.services.put(service.typeService(),list);
		} else {//Se existir basta inserir na lista existente
			List<ServiceI> list = null;
			list = services.get(service.typeService());
			list.add(service);
			this.services.put(service.typeService(),list);
		}
		return;
    }
	
	/*
		Recepção de agentes, passando-lhes uam lista dos serviços desejados e removendo a agência
		de sua lista de destinos
	*/
	public int receiveAgent(AgenteMovelI agent) throws RemoteException 
    { 
			System.out.println("Agent received, service wanted : "+agent.serviceWant());
			agent.execute(services.get(agent.serviceWant()));
			agent.removeDestination(HOST+this.name);
			this.sendAgent(agent);	
			return 0;
    }
	
	
	/*
		Envio de agente
	*/
	public int sendAgent(AgenteMovelI agent) throws RemoteException 
    { 
		if(agent.nextDestination() == null) {//Sem destinos próximos
			AgenciaLancadoraI source = null;
			source = agent.getSource();
			source.notifyMsg();//mensagem de notificação à agência lançadora
		} else {//Destinos a passar
			try {  			
				AgenciaI nextAgencia = null;  
				nextAgencia = (AgenciaI) Naming.lookup(agent.nextDestination());
				nextAgencia.receiveAgent(agent);	
			} catch (Exception e) {
				System.err.println("RMI server exception:" + e);
				e.printStackTrace();
			}	
		}					 		
		return 0;
    }
	
	public int isAlive() throws RemoteException 
    { 
		System.out.println("I'm alive");
		return 1;
    }
    /*
		Retorna tipos serviços oferecidos pela agência
		no formato : x,y,z,w ... n
	*/	
    public String oferedService() throws RemoteException{
	
		if(!services.isEmpty()) {
		  
		  String kind = "";//String com serviços
		  int i = 1; //token para virgula 
		  
		  //Interador para loop no mapa
		  Iterator it = services.entrySet().iterator();  
		  while (it.hasNext()) {
		  
			Map.Entry entry = (Map.Entry)it.next();
			//System.out.println(pairs.getKey() + " = " + pairs.getValue());
			if(i<services.size())
			kind += entry.getKey()+",";
			else
			kind += entry.getKey();			 
			i++;
		  } 	  
		  return kind;
		} 
	 return null;	
    }
	
	public static void main(String args[]) throws RemoteException{
        
		System.out.println("Agência iniciando...");
		// Create and install a security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
            System.out.println("Gerenciamento de segurança instalado com sucesso.");
        } else {
            System.out.println("Gerenciamento de segurança já existe.");
        }
		
		/*REGISTER - HOST*/
        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099); //port number
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
		
		try {   
			AgenciaI AgenciaB = new AgenciaB();
			AgenciaB.setName("Beta");
			
			ServiceI service1 = new ServiceInformation(); 
			AgenciaB.addService(service1);
			
			Naming.rebind("//localhost/"+AgenciaB.getName(), AgenciaB);
			System.out.println("\n"+AgenciaB.getName()+" is ready.");
       } catch (Exception e) {
            System.err.println("RMI server exception:" + e);
            e.printStackTrace();
       }
		
    }
}
