import java.io.Serializable;

public interface ServiceI extends Serializable{
    public Object doService(); //executa servi�o
    public int typeService();  //retorna identificador do servi�o pr� determinado pelo sistema
}
