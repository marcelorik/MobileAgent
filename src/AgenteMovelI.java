import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
 
public interface AgenteMovelI extends java.io.Serializable {
    public void execute(List<ServiceI> list); //Executa os serviços passados pela agência
    public int serviceWant(); //Retorna codigo dos serviços que devem ser executados
	public void loadDestination(List<String> list); //Carrega as agências a serem visitadas
	public void removeDestination(String agencia); //Remove agência da lista através da url do parâmetro
	public String nextDestination(); //Retorna uma agência da lista a ser visitasa
	public void setSource(AgenciaLancadoraI source); //Carrega referência da agência lançadora caso precise da mesma
	public AgenciaLancadoraI getSource(); //Retorna referência da agência lançadora
}
