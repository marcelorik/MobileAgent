package ufu.sd.mobilePlatform;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;


/**
 * Classe com funções utilitárias em comum.
 *
 */
public class Utility {
	/** Instala um security manager, caso já não haja um.
	 */
	public static void installSecurityManager() {
		
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
            System.out.println("Security Manager succesfully installed.");
        } else {
            System.out.println("Security Manager already exists.");
        }
	}
	
	/** Inicia o registry, caso já não haja um.
	 */
	public static void installRegistry() {
        try { 
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            System.out.println("java RMI registry already exists.");
        }
	}
	
	/** Dá parse nos argumentos da linha de comando.
	 * @param args Array de argumentos
	 * @return Mapa no formato opção: valor
	 */
	public static HashMap<String,String> parseArgs(String args[]) {
		HashMap<String,String> argMap = new HashMap<String,String>();
		
		for (int i=0; i<args.length; i++) {
			// Se começa com -- é porque é uma opção.
			if (args[i].startsWith("--")) {
				String key = args[i].substring(2);
				String value = null;
				
				// Vê se há algo (que não seja começado em --) na próxima
				// posição.
				if ((i+1) < args.length && !args[i+1].startsWith("--")) {
					value = args[i+1];
					i++;
				}
				
				argMap.put(key, value);
			}
		}
		
		return argMap;
	}
}
