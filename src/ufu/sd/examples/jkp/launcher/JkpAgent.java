package ufu.sd.examples.jkp.launcher;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Queue;

import ufu.sd.mobilePlatform.Agency;
import ufu.sd.mobilePlatform.Agent;
import ufu.sd.mobilePlatform.IAgency;
import ufu.sd.mobilePlatform.ILauncher;
import ufu.sd.mobilePlatform.exception.UnavailableServiceException;


/** Agente que requisita serviços de Jokenpo nas agências.
 */
public class JkpAgent extends Agent {
	
	/** Classe utilizada para guardar os resultados dos jogos.
	 */
	private class JkpResult implements java.io.Serializable {
		private String myMove;
		private String opMove;
		private final String RES_W = "WON";
		private final String RES_L = "LOST";
		private final String RES_D = "DRAW";
		
		JkpResult(String myMove, String opMove) {
			this.myMove = myMove;
			this.opMove = opMove;
		}
		
		public String toString() {
			return "I played "+myMove+", agency played "+opMove+". Result: "+result();
		}
		
		
		/** Compara os resultados de suas jogadas com as dos
		 * oponentes e vê qual foi o resultado do Jokenpo.
		 */
		private String result() {
			if (myMove.equals(opMove)) {
				return RES_D;
			} else if (myMove.equals("rock")) {
				if (opMove.equals("scissors"))
					return RES_W;
				else
					return RES_L;
			} else if (myMove.equals("paper")) {
				if (opMove.equals("rock"))
					return RES_W;
				else
					return RES_L;
			} else {
				if (opMove.equals("paper"))
					return RES_W;
				else
					return RES_L;
			}
		}
	}
	
	/** Lista dos resultados dos jogos contra as agências em que 
	 *  o Agente já visitou.
	 */
	private ArrayList<JkpResult> results = new ArrayList<JkpResult>();

	public JkpAgent(String name, ILauncher source) {
		super(name,source);
		ArrayList<String> servicesWanted = new ArrayList<String>();
		servicesWanted.add("JOKENPO");
		this.setServicesWanted(servicesWanted);
	}
	
	public void execute(IAgency agency) {
		String myMove = null;
		String opponentMove = null;
		
		try {
			myMove = (String) agency.runService("JOKENPO",null);
			opponentMove = (String) agency.runService("JOKENPO",null);
		} catch (RemoteException e) {			
			System.out.println(e.getMessage());
		} catch (UnavailableServiceException e) {			
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException  e) {			
			System.out.println(e.getMessage());
		} catch (ClassCastException e) {			
			System.out.println(e.getMessage());
		}
		
		finally {
			collectResult(myMove,opponentMove);
		}
		
		this.walk();
	}

	
	/** Adiciona resultado à lista de resultados.
	 * @param my Jogada do Agente
	 * @param his Jogada do oponente (Agência);
	 */
	private void collectResult(String my, String his) {
		results.add(new JkpResult(my,his));
	}

	public void report() {
		System.out.println("Agent "+this.getName()+" reporting!");
		System.out.println("Played "+results.size()+" games.");
		System.out.println("Results:");
		
		for (JkpResult res : results)
			System.out.println((results.indexOf(res)+1)+") "+res);
		
	}
}
