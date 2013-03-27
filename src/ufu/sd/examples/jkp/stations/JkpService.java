package ufu.sd.examples.jkp.stations;

import ufu.sd.mobilePlatform.IService;

/** Serviço que joga Jokenpo.
 */
public class JkpService implements IService{
	
	public Object run(Object[] args) throws IllegalArgumentException, ClassCastException {
		return makeMove();
	}
	
	
	/** Gera um número aleatório de 1 a 3 e retorna a string
	 * correspondente à jogada.
	 */
	private static String makeMove() {
		int move = 1 + (int)(Math.random() * ((3 - 1) + 1));
		return moveToString(move);
	}
	
	/** Transforma uma jogada de número para string
	 * @param move Jogada
	 * @return pedra, papel ou tesoura
	 */
	private static String moveToString(int move) {
		if (move == 1)
			return "rock";
		else if (move == 2)
			return "paper"; 
		else
			return "scissors";
	}
	
	public String getName() {
		return "JOKENPO";
	}
	
}
