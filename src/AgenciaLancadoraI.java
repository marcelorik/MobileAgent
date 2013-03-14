import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
 
public interface AgenciaLancadoraI extends Remote {  
	public void notifyMsg() throws RemoteException; //Reporta na agência laçadora término de um agente
	public void notifyMsg(Object obj) throws RemoteException; //Reporta relatório caso necessário
	public void agentInstruction(AgenteMovelI agent) throws RemoteException; //Carrega o trajeto para o agente e sua referência
	public void refreshMaps() throws RemoteException; //Atualiza as agências e seus serviços disponiveis
	public List<String> travel(int service) throws RemoteException; //Retorna o trajeto do agente
	public void fillOut(String nameRegistry,AgenciaI agencia) throws RemoteException; //Preenche as listas de serviços e agências
}
