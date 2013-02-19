import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
 
public interface AgenteMovelI extends java.io.Serializable {
    public void execute(List<ServiceI> list);
    public int serviceWant(); //Retorna codigo dos serviços que devem ser executados
	public void loadDestination(List<String> list);
	public void removeDestination(String agencia);
	public String nextDestination();
	public void setSource(AgenciaLancadoraI source);
	public AgenciaLancadoraI getSource();
}
