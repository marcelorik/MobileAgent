package ufu.sd.mobilePlatform;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ufu.sd.mobilePlatform.exception.ServiceClashException;
import ufu.sd.mobilePlatform.exception.UnavailableServiceException;

/** Interface que representa as agências remotas.
 */
public interface IAgency extends Remote {
	
	
	/** Recebe um agente.
	 * @param agente Agente a ser recebido
	 */
	public void receiveAgent(IAgent agente) throws RemoteException; 
	
	/** Adiciona serviço aos serviços que a agência oferece.
	 * @param service Serviço a ser adicionado.
	 * @throws ServiceClashException Utilizada caso já haja um serviço com o mesmo nome 
	 * 	(visto que o nome é utilizado para identificar os serviços)
	 */
	public void addService(IService service) throws RemoteException, ServiceClashException;
	
	/** Vê se a agência ainda está funcionando.
	 * @return true
	 */
	public boolean isAlive() throws RemoteException;
	
	/** Pega nome da agência.
	 * @return Nome da agência.
	 */
	public String getName() throws RemoteException;
	
	/** Vê quais serviços a agência oferece.
	 * @return ArrayList com os serviços oferecidos.
	 */
	public ArrayList<String> availableServices() throws RemoteException;
	
	/** Verifica se a agência possui um serviço.
	 * @param wanted Nome do serviço que se quer verificar.
	 * @return true caso o serviço esteja disponível.
	 */
	public boolean hasService(String wanted) throws RemoteException;
	
	/** Executa serviço.
	 * @param name Nome do serviço.
	 * @param args Array de argumentos a serem passados pro serviço.
	 * @return Resultado da execução.
	 * @throws UnavailableServiceException Exceção tacada caso o serviço requisitado
	 * 	não esteja disponível.
	 */
	public Object runService(String name, Object[] args) throws RemoteException, UnavailableServiceException;
}
