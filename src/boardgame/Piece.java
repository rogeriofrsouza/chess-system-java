package boardgame;

public abstract class Piece {

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
	
	public abstract boolean[][] possibleMoves();
	
	/*
	 *  Hook method: método que faz um gancho com a subclasse.
	 *  Chama uma possível implementação de alguma subclasse concreta da classe Piece
	 */
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
