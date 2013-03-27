package ufu.sd.mobilePlatform;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

 
/** Interface de um Monitor de agências, responsável por
 * manter uma lista de agências e oferecer funções úteis
 * relacionadas a elas.
 */
public interface IMonitor extends Remote { 
	
	/** Obter lista de agências que o Monitor está tomando conta.
	 * @return Lista de agências que o monitor toma conta.
	 */
	public ArrayList<IAgency> getAgencies() throws RemoteException;
	
	/** Vê todas as agências (dentre as quais ele toma conta) que podem
	 * oferecer todos os serviços desejados.
	 * @param services Serviços desejados.
	 * @return Fila de agências que possuem todos serviços desejados.
	 */
	public Queue<IAgency> getAgenciesByServices(ArrayList<String> services) throws RemoteException;
	
	/** Registra agência na lista de agências do Monitor.
	 * @param agency Agência a ser registrada.
	 */
	public void registerAgency(IAgency agency) throws RemoteException;
	
	/** Remove agência da lista de agências do Monitor.
	 * @param agency Agência a ser removida.
	 * @return true caso a Agência tenha sido removida
	 */
	public boolean unregisterAgency(IAgency agency) throws RemoteException;
}
