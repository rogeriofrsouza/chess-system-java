package com.rogeriofrsouza.app.ui;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.rogeriofrsouza.app.ui.AnsiEscapeCode.*;

public class Display {

    public void clearScreen() {
        System.out.printf("%s%s", MOVE_CURSOR_HOME, CLEAR_SCREEN);
    }

    public void printMatch(ChessMatch chessMatch) {
        printBoard(chessMatch.getBoard());

        var stringBuilder = new StringBuilder();
        stringBuilder.append("Captured pieces");

        formatCapturedPieces(chessMatch, stringBuilder);

        stringBuilder.append("\nTurn: ").append(chessMatch.getTurn());

        if (chessMatch.isCheckMate()) {
            stringBuilder.append("\nCHECKMATE!\nWinner: ")
                    .append(formatPlayerColor(chessMatch.getCurrentPlayer()));

            System.out.println(stringBuilder);
            return;
        }

        stringBuilder.append("\nWaiting player: ")
                .append(formatPlayerColor(chessMatch.getCurrentPlayer()));

        if (chessMatch.isCheck()) {
            stringBuilder.append("\nCHECK!");
        }

        System.out.println(stringBuilder);
    }

    private String formatPlayerColor(ChessPiece.Color color) {
        return getColorAnsiCode(color) + color.getValue() + RESET;
    }

    private void formatCapturedPieces(ChessMatch chessMatch, StringBuilder stringBuilder) {
        Arrays.stream(ChessPiece.Color.values())
                .map(color -> {
                    List<ChessPiece> pieces = chessMatch.getCapturedPieces()
                            .stream()
                            .map(ChessPiece.class::cast)
                            .filter(p -> p.getColor() == color)
                            .toList();

                    return "%n%s: %s%s%s".formatted(
                            color.getValue(), getColorAnsiCode(color), pieces, RESET);
                })
                .forEach(stringBuilder::append);
    }

    public void printBoard(Board board) {
        clearScreen();

        var stringBuilder = new StringBuilder();
        var rowCount = new AtomicInteger();

        Arrays.stream(board.getSquares()).forEach(row -> {
            stringBuilder.append(8 - rowCount.getAndIncrement()).append(" ");

            Arrays.stream(row).forEach(square -> {
                if (square.isPossibleMove()) {
                    stringBuilder.append(BLUE_BACKGROUND);
                }

                var piece = (ChessPiece) square.getPiece();
                if (piece == null) {
                    stringBuilder.append("-");
                } else {
                    String color = getPieceAnsiColor(piece);
                    stringBuilder.append(color).append(piece);
                }

                stringBuilder.append(RESET).append(" ");
            });

            stringBuilder.append("\n");
        });

        stringBuilder.append("  a b c d e f g h\n");
        System.out.println(stringBuilder);
    }

    private String getPieceAnsiColor(ChessPiece piece) {
        Objects.requireNonNull(piece, "ChessPiece cannot be null");
        ChessPiece.Color color = piece.getColor();

        return getColorAnsiCode(color);
    }

    private String getColorAnsiCode(@NonNull ChessPiece.Color color) {
        return color == ChessPiece.Color.WHITE ? WHITE.getValue() : YELLOW.getValue();
    }
}
