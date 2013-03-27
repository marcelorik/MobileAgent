package ufu.sd.mobilePlatform;
import java.rmi.RemoteException;
import java.util.*;


/**Implementação de um Agente Móvel.
 */
public abstract class Agent implements IAgent { 
	
	private ArrayList<String> servicesWanted;
	private Queue<IAgency> destinations;
	private ILauncher source = null;
	private String name;
    
	public Agent(String name, ILauncher source) {
		this.name = name;
		this.source = source;
	}
	
	public void setServicesWanted(ArrayList<String> services) {
		this.servicesWanted = services;	
	}
	
	/** Vê qual é o próximo destino e retira ele da fila.
	 * @return Próximo destino (Agência).
	 */
	private IAgency nextDestination(){
		if (destinations == null)
			return null;
		
		return destinations.poll();
    }
	
	
	/** Volta para a agência lançadora de origem.
	 */
	private void returnHome() {
		try {
			source.receiveBack(this);
		} catch (RemoteException e) {
			System.out.println("I'm agent "+this.getName()+" and I'm homeless, so I'll report here...");
			this.report();
		}
	}

	public ArrayList<String> servicesWanted() {
		return servicesWanted;
	}

	/** Configura uma fila de destinos e tenta caminhar
	 * para o próximo destino.
	 * @param queue Fila de destinos.
	 */
	public void setDestinations(Queue<IAgency> queue) {
		this.destinations = queue;
		this.walk();
	}

	public ILauncher getSource(){
		return this.source;
    }

	public String getName() {
		return name;
	}
	
	/** Caminha para o próximo destino.
	 */
	protected void walk()  {
		IAgency next = this.nextDestination();		
		if (next == null)
			this.returnHome();
		else
			try {
				next.receiveAgent(this);
			} catch (RemoteException e) {
				System.out.println("I'm agent "+this.getName()+" and I'm skipping one destination...");
				this.walk();
			}
	}
}
