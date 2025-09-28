package com.rogeriofrsouza.app;

import com.rogeriofrsouza.app.chess.ChessException;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.ChessPosition;
import com.rogeriofrsouza.app.ui.Display;
import com.rogeriofrsouza.app.ui.Prompt;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        Prompt prompt = new Prompt(scanner);

        Display display = new Display();
        ChessMatch chessMatch = new ChessMatch();

        do {
            try {
                display.printMatch(chessMatch);

                ChessPosition source = prompt.readSourcePosition();
                chessMatch.computePossibleMoves(source);

                display.printBoard(chessMatch.getBoard());

                ChessPosition target = prompt.readTargetPosition();
                chessMatch.performChessMove(source, target);

                if (chessMatch.getPromoted() != null) {
                    ChessPiece.Name pieceName = prompt.readPromotedPiece();
                    chessMatch.replacePromotedPiece(pieceName);
                }
            } catch (ChessException | InputMismatchException | IllegalArgumentException exception) {
                System.err.println(exception.getMessage());
                scanner.nextLine();
            }

            chessMatch.resetPossibleMoves();
        } while (!chessMatch.isCheckMate());

        display.printMatch(chessMatch);
    }
}
