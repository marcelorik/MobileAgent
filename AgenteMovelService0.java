import java.util.*;

public class AgenteMovelService0 implements AgenteMovelI
{ 
    // "obj" is the reference of the remote object
    private AgenciaI obj = null;
	private static final int SERVICE = 0;
	private List<String> listaAgencias;
	private AgenciaLancadoraI source = null;
	
    
    public AgenteMovelService0()
    {
		super();
    } 

    public void execute(List<ServiceI> list)
    {
		for(ServiceI key: list){
			key.doService();	
		}	
    }
    
    public void loadDestination(List<String> list){
		listaAgencias = list;
		return;
    }
	
	public void setSource(AgenciaLancadoraI source){
		this.source = source;
		return;
    }
	
	public AgenciaLancadoraI getSource(){
		return this.source;
    }
	
    public void removeDestination(String agencia){
		listaAgencias.remove(agencia);
		return;
    }

	public String nextDestination(){
		if(!listaAgencias.isEmpty()) 
			return listaAgencias.get(0);
		else	
			return null;
    }
    
     public int serviceWant(){
      return SERVICE;
    }
}
