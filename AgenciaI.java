import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface AgenciaI extends Remote {
    public String oferedService() throws RemoteException; //Retorna tipos serviços oferecidos pela agência;
	public int receiveAgent(AgenteMovelI agente) throws RemoteException;
	public int sendAgent(AgenteMovelI agente) throws RemoteException;
	public void addService(ServiceI service) throws RemoteException;
	public void setName(String name) throws RemoteException;
	public int isAlive() throws RemoteException;
	public String getName() throws RemoteException; 
}
