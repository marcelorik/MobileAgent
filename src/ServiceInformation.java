

public class ServiceInformation implements ServiceI
{ 
    //Tipo serviço pré definido
	public static final int SERVICE = 0;
	
    public ServiceInformation(){
      super();
     }
     
   /* public ServiceInformation(int type){
      super();
      this.type = type;
    }*/
 
    public Object doService()
    {      
      Runtime runTime = Runtime.getRuntime();
	  System.out.print("\nInformações do Sistema \n");  
      System.out.print("Memoria livre: ");  
      System.out.println(runTime.freeMemory());  
      System.out.print("Memoria total: ");  
      System.out.println(runTime.totalMemory());  
      System.out.print("Processadores: ");  
      System.out.println(runTime.availableProcessors()); 
      System.out.print("Nome SO: ");  
      System.out.println(System.getProperty("os.name"));  
      System.out.print("Arquitetura SO: ");   
      System.out.println(System.getProperty("os.arch"));
      System.out.print("Versão SO: ");    
      System.out.println(System.getProperty("os.version"));
      System.out.print("Usuario: ");  
      System.out.println(System.getProperty("user.name"));
      System.out.print("Diretorio: ");  
      System.out.println(System.getProperty("user.home"));
      //System.out.print("General Information: "); 
      //System.out.println(System.getProperties());
      System.out.print("\n\nScan concluído.\n"); 
	  return 0;
    }
    
    public int typeService(){
      return SERVICE;
    }

}
