package ufu.sd.mobilePlatform;
import java.rmi.RemoteException;
import java.util.*;

 
/** Interface que representa um Agente Móvel.
 */
public interface IAgent extends java.io.Serializable {
	
    /** Executa seu código.
     * @param agency Agência onde ele está sendo executado.
     */
    public void execute(IAgency agency); 
    
    /** Vê serviços que o agente precisa.
     * @return Lista com o código (nome) dos serviços que o agente necessita.
     */
    public ArrayList<String> servicesWanted(); 
    
    /** Configura os serviços que o agente necessita.
     * @param services Lista com o código (nome) dos serviços que o agente necessita.
     */
    public void setServicesWanted(ArrayList<String> services);
	
    /** Configura destinos que o agente deve percorrer.
     * @param queue Fila com os destinos que o agente deve percorrer.
     */
    public void setDestinations(Queue<IAgency> queue); 
	
    /** Vê origem do agente.
     * @return Launcher de origem.
     */
    public ILauncher getSource(); //Retorna referência da agência lançadora
	
    /** Vê nome do agente.
     * @return Nome do agente.
     */
    public String getName();
	
    /**Faz com que o agente reporte os resultados que acumulou.
     */
    public void report();
}
