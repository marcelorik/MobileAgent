import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
 
public interface AgenciaLancadoraI extends Remote {  
	public void notifyMsg() throws RemoteException; //Reporta na ag�ncia la�adora t�rmino de um agente
	public void notifyMsg(Object obj) throws RemoteException; //Reporta relat�rio caso necess�rio
	public void agentInstruction(AgenteMovelI agent) throws RemoteException; //Carrega o trajeto para o agente e sua refer�ncia
	public void refreshMaps() throws RemoteException; //Atualiza as ag�ncias e seus servi�os disponiveis
	public List<String> travel(int service) throws RemoteException; //Retorna o trajeto do agente
	public void fillOut(String nameRegistry,AgenciaI agencia) throws RemoteException; //Preenche as listas de servi�os e ag�ncias
}
