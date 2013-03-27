package ufu.sd.mobilePlatform;
import java.io.Serializable;

/**Interface dos serviços que podem ser oferecidos
 * pelas Agências.
 */
public interface IService extends Serializable {
    
	/** Executa o serviço.
	 * @param args Lista de parâmetros para a execução. O serviço que implementa deve dar cast
	 * nos parâmetros para os tipos que precisa, bem como conferir a quantidade de argumentos.
	 * @return Resultado da execução do serviço. O objeto que requisitar o serviço deve saber o tipo
	 * esperado e dar cast no retorno.
	 * @throws IllegalArgumentException O serviço pode lançar essa exceção caso o número de argumentos
	 * não seja esperado.
	 * @throws ClassCastException O serviço pode lançar essa exceção caso haja erro ao dar cast nos argumentos.
	 */
	public Object run(Object[] args) throws IllegalArgumentException, ClassCastException; 
	
	public String getName();
}
