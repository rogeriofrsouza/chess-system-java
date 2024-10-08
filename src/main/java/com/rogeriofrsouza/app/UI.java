package com.rogeriofrsouza.app;

import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.ChessPosition;
import com.rogeriofrsouza.app.chess.Color;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    // https://gist.github.com/ConnerWill/d4b6c776b509add763e17f9f113fd25b
    public static final String ANSI_MOVE_CURSOR_HOME = "\033[H";
    public static final String ANSI_CLEAR_SCREEN = "\033[2J";

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public void clearScreen() {
        System.out.print(ANSI_MOVE_CURSOR_HOME + ANSI_CLEAR_SCREEN);
    }

    public void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces(), null);

        List<ChessPiece> white =
                captured.stream()
                        .filter(piece -> piece.getColor() == Color.WHITE)
                        .collect(Collectors.toList());

        List<ChessPiece> black =
                captured.stream()
                        .filter(piece -> piece.getColor() == Color.BLACK)
                        .collect(Collectors.toList());

        System.out.println("\nCaptured pieces");

        System.out.printf(
                "White: %s%s%n%sBlack: %s%s%n%s",
                ANSI_WHITE, white, ANSI_RESET, ANSI_YELLOW, black, ANSI_RESET);

        System.out.println("\nTurn: " + chessMatch.getTurn());

        if (chessMatch.isCheckMate()) {
            System.out.println("CHECKMATE!\nWinner: " + chessMatch.getCurrentPlayer());
            return;
        }

        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());

        if (chessMatch.isCheck()) {
            System.out.println("CHECK!");
        }
    }

    public void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < pieces.length; i++) {
            stringBuilder.append((8 - i) + " ");

            for (int j = 0; j < pieces[i].length; j++) {
                if (possibleMoves != null && possibleMoves[i][j]) {
                    stringBuilder.append(ANSI_BLUE_BACKGROUND);
                }

                if (pieces[i][j] == null) {
                    stringBuilder.append("-");
                } else {
                    String color =
                            pieces[i][j].getColor() == Color.WHITE ? ANSI_WHITE : ANSI_YELLOW;
                    stringBuilder.append(color + pieces[i][j]);
                }

                stringBuilder.append(ANSI_RESET + " ");
            }

            stringBuilder.append("\n");
        }

        stringBuilder.append("  a b c d e f g h");
        System.out.println(stringBuilder.toString());
    }

    public ChessPosition readChessPosition(Scanner scanner) {
        String input = scanner.nextLine();

        if (!input.matches("[a-h][1-8]")) {
            throw new InputMismatchException(
                    "Error reading ChessPosition. Valid values are from a1 to h8.");
        }

        char column = input.charAt(0);
        int row = Character.getNumericValue(input.charAt(1));

        return new ChessPosition(column, row);
    }
}
