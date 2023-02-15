package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

// Coração do sistema de xadrez, contém as regras do jogo de xadrez
public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);  // Esta classe deve saber as dimensões de um jogo de xadrez
		
		initialSetup();
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
	
	// Coloca peça recebendo a posição nas coordenadas do xadrez
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	// Inicia a partida de xadrez colocando as peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
	}
	
}
