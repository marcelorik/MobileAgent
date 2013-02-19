

public class ServiceHello implements ServiceI
{ 
    //Tipo serviço pré definido
    private int type = 2;

    public ServiceHello(){
      super();
     }
     
    public ServiceHello(int type){
      super();
      this.type = type;
    }
 
    public Object doService()
    {         
      System.out.print("\nHello World!\n"); 
	  return 0;
    }
    
    public int typeService(){
      return type;
    }

}
