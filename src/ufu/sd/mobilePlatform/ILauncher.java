package ufu.sd.mobilePlatform;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

 
/**Interface de uma Agência Lançadora
 */
public interface ILauncher extends Remote {  
	
	/** Recebe um agente que havia lançado.
	 * @param agent Agente que havia lançado.
	 */
	public void receiveBack(IAgent agent) throws RemoteException;
	
	/** Lança um agente.
	 * @param agent Agente a ser lançado.
	 */
	public void launch(IAgent agent) throws RemoteException;
}
