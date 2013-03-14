import java.io.Serializable;

public interface ServiceI extends Serializable{
    public Object doService(); //executa serviço
    public int typeService();  //retorna identificador do serviço pré determinado pelo sistema
}
