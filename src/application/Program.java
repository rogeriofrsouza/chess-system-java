package application;

import java.util.Locale;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		// Desenvolvimento em camadas: o programa principal deve reconhecer apenas a camada de xadrez e nãod e tabuleiro
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);	
		ChessMatch chessMatch = new ChessMatch();
		
		while (true) {	
			UI.printBoard(chessMatch.getPieces());
			
			System.out.println();
			System.out.print("Source: ");
			ChessPosition source = UI.readChessPosition(sc);
			
			System.out.println();
			System.out.print("Target: ");
			ChessPosition target = UI.readChessPosition(sc);
			
			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		}
		
	}

}
