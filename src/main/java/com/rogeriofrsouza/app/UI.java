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
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // https://gist.github.com/ConnerWill/d4b6c776b509add763e17f9f113fd25b
    public static final String ANSI_MOVE_CURSOR_HOME = "\033[H";
    public static final String ANSI_CLEAR_SCREEN = "\033[2J";

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public void clearScreen() {
        System.out.print(ANSI_MOVE_CURSOR_HOME + ANSI_CLEAR_SCREEN);
        System.out.flush();
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

        System.out.printf(
                "%nCaptured pieces%nWhite: %s%s%n%sBlack: %s%s%n%s",
                ANSI_WHITE, white, ANSI_RESET, ANSI_YELLOW, black, ANSI_RESET);

        System.out.println("\nTurn: " + chessMatch.getTurn());

        if (chessMatch.getCheckMate()) {
            System.out.println("CHECKMATE!\nWinner: " + chessMatch.getCurrentPlayer());
            return;
        }

        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());

        if (chessMatch.getCheck()) {
            System.out.println("CHECK!");
        }
    }

    public void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");

            for (int j = 0; j < pieces[i].length; j++) {
                if (possibleMoves != null && possibleMoves[i][j]) {
                    System.out.print(ANSI_BLUE_BACKGROUND);
                }

                if (pieces[i][j] == null) {
                    System.out.print("-");
                } else {
                    String color =
                            pieces[i][j].getColor() == Color.WHITE ? ANSI_WHITE : ANSI_YELLOW;
                    System.out.print(color + pieces[i][j]);
                }

                System.out.print(ANSI_RESET + " ");
            }

            System.out.println();
        }

        System.out.println("  a b c d e f g h");
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
