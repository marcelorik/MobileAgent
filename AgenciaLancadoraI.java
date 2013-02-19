import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
 
public interface AgenciaLancadoraI extends Remote {  
	public void notifyMsg() throws RemoteException;
	public void agentInstruction(AgenteMovelI agent) throws RemoteException;
	public void refreshMaps() throws RemoteException;
	public List<String> travel(int service) throws RemoteException;
	public void fillOut(String nameRegistry,AgenciaI agencia) throws RemoteException;
}
