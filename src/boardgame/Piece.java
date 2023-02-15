package boardgame;

public class Piece {

	// Não é ainda a posição do xadrez, é uma posição simples de matriz. Não deve ser visível na camada de xadrez
	protected Position position;
	private Board board;
	
	// A posição de uma peça recém criada será nula, pois ainda não foi colocada no tabuleiro
	public Piece(Board board) {
		this.board = board;
		// position = null;
	}

	// Somente classes do mesmo pacote e subclasses podem acessar o Board
	protected Board getBoard() {
		return board;
	}

	// Não permitir que o Board seja alterado. Remover o setBoard()
	
}
