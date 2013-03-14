import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*; 
import java.util.*;
 
 
 //se implementar REMOTE passa this para o agente!! show de bola
public class AgenciaLancadora 
	extends UnicastRemoteObject 
    implements AgenciaLancadoraI {
  
  
    private HashMap<String, List<Integer>> mapAgencias = new HashMap<String, List<Integer>>();
    private HashMap<Integer, List<String>> mapServicos = new HashMap<Integer, List<String>>();
	 
    public AgenciaLancadora() throws RemoteException {
      super();
    }
	
	public void fillOut(String nameRegistry,AgenciaI agencia) throws RemoteException {
       try
		{ 	
			String[] tokens = (agencia.oferedService()).split(","); //Lista de serviços oferecidos pela agência
			
			//Preenchendo Mapa de Agencia -> Serviços  - Caso seja refresh ele irá substituir a lista antiga
			List<Integer> listServices = new ArrayList<Integer>();			
			for(String key : tokens) {
				listServices.add(Integer.parseInt(key));
			}
			mapAgencias.put(nameRegistry,listServices);			
	  
			//Preenchendo Mapa de Serviços - > Agencia			
			for(String key : tokens) {
				if(!mapServicos.containsKey(Integer.parseInt(key))) { //Não existe serviços no mapa
					List<String> listAgencias = new ArrayList<String>();
					listAgencias.add(nameRegistry);
					mapServicos.put(Integer.parseInt(key),listAgencias);				
				} else { //Incrementa agências do serviço já existente
					List<String> listAgencias = null;
					listAgencias = mapServicos.get(Integer.parseInt(key));					
					listAgencias.add(nameRegistry);
					mapServicos.put(Integer.parseInt(key),listAgencias);				
				}
			}
		
		} catch(Exception e){
            System.err.println("RmiClient exception: " + e); 
            e.printStackTrace(); 
        } 
    }
	
	
	/* Mensagem chamada dentro do agente para indicar seu término */
	public void notifyMsg() throws RemoteException {
		System.out.println("One of the agent's expired after finishing its task.");
    }
	
	public void notifyMsg(Object obj) throws RemoteException {
		System.out.println(obj.toString());
    }
	
	
	public List<String> travel(int service) throws RemoteException {
		return mapServicos.get(service);
	}
	
	/*
		Preenche os mapas Agencia -> Serviços / Serviços - > Agencia, caso estejam vazios ou
		atualizam os mesmos.
	*/
	public void refreshMaps() throws RemoteException {
	
		 try {  
		
			AgenciaI agencia = null;   		

			String[] listAgencia;
			listAgencia = Naming.list("//localhost/");
			for(String key: listAgencia) {
				agencia = (AgenciaI) Naming.lookup(key);
				fillOut(key,agencia);         
			} 
			
		} catch (Exception e) {
            System.err.println("RMI server exception:" + e);
            e.printStackTrace();
        }
	
	}
	
	/*
		Chamada de encaminhamento dos agentes (Maior granulalidade nos serviços)
	*/	
	public void agentInstruction(AgenteMovelI agent) throws RemoteException {
	
		try {  
			agent.loadDestination(travel(agent.serviceWant()));
			agent.setSource(this);
			
			//Obtendo agencia a enviar
			AgenciaI agencia = null;		
			agencia = (AgenciaI) Naming.lookup(agent.nextDestination());
			agencia.receiveAgent(agent);
		} catch (Exception e) {
            System.err.println("RMI server exception:" + e);
            e.printStackTrace();
        }
	}
  
    public static void main(String args[])  throws RemoteException {
        
		System.out.println("Agência lançadora iniciando...");		
		AgenciaLancadora agenciaL = new AgenciaLancadora();
		
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
		
		System.out.println("\nAgência Lançadora is ready.");		
					
		agenciaL.refreshMaps();
		AgenteMovelI agent = new AgenteMovelService0();
		agenciaL.agentInstruction(agent);

    }
        
}