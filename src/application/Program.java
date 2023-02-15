package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		
		// Desenvolvimento em camadas: o programa principal deve reconhecer apenas a camada de xadrez e nãod e tabuleiro
		
		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.getPieces());
		
	}

}
