package com.rogeriofrsouza.app.ui;

import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.rogeriofrsouza.app.ui.AnsiEscapeCode.*;

public class Display {

    public void clearScreen() {
        System.out.printf("%s%s", MOVE_CURSOR_HOME, CLEAR_SCREEN);
    }

    public void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces(), null);

        List<ChessPiece> white = captured.stream()
            .filter(piece -> piece.getColor() == ChessPiece.Color.WHITE)
            .toList();

        List<ChessPiece> black = captured.stream()
            .filter(piece -> piece.getColor() == ChessPiece.Color.BLACK)
            .toList();

        System.out.println("\nCaptured pieces");

        System.out.printf("White: %s%s%n%sBlack: %s%s%n%s",
                WHITE, white, RESET, YELLOW, black, RESET);

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
        clearScreen();
        StringBuilder stringBuilder = new StringBuilder();

        IntStream.range(0, pieces.length).forEach(i -> {
            stringBuilder.append((8 - i)).append(" ");

            IntStream.range(0, pieces[i].length).forEach(j -> {
                if (possibleMoves != null && possibleMoves[i][j]) {
                    stringBuilder.append(BLUE_BACKGROUND);
                }

                if (pieces[i][j] == null) {
                    stringBuilder.append("-");
                } else {
                    String color = getPieceAnsiColor(pieces[i][j]);
                    stringBuilder.append(color).append(pieces[i][j]);
                }

                stringBuilder.append(RESET).append(" ");
            });

            stringBuilder.append("\n");
        });

        stringBuilder.append("  a b c d e f g h");
        System.out.println(stringBuilder);
    }

    private String getPieceAnsiColor(ChessPiece piece) {
        Objects.requireNonNull(piece, "ChessPiece cannot be null");
        ChessPiece.Color color = piece.getColor();

        return color == ChessPiece.Color.WHITE ? WHITE.getValue() : YELLOW.getValue();
    }
}
