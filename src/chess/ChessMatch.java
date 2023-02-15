package chess;

import boardgame.Board;

// Coração do sistema de xadrez, contém as regras do jogo de xadrez
public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);  // Esta classe deve saber as dimensões de um jogo de xadrez
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);  // Downcasting
			}
		}
		
		return mat;
	}
	
}
