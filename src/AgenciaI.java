import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface AgenciaI extends Remote {
    public String oferedService() throws RemoteException; //Retorna tipos serviços oferecidos pela agência;
	public int receiveAgent(AgenteMovelI agente) throws RemoteException; //Recebe agente a ser tratado
	public int sendAgent(AgenteMovelI agente) throws RemoteException; //Envia agente caso o mesmo ainda possua destino
	public void addService(ServiceI service) throws RemoteException; //Adiciona serviço a uma agência
	public void setName(String name) throws RemoteException; //Carrega o nome da agência
	public int isAlive() throws RemoteException; //Retorna mensagem para identificação de falhas
	public String getName() throws RemoteException; //Retorna nome da agência
}
