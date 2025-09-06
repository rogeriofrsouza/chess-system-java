package com.rogeriofrsouza.app.ui;

import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.ChessPosition;
import lombok.RequiredArgsConstructor;

import java.util.InputMismatchException;
import java.util.Scanner;

@RequiredArgsConstructor
public class Prompt {

    private final Scanner scanner;

    private String readInput() {
        String input = scanner.nextLine();

        if (input == null || input.trim().isBlank()) {
            throw new InputMismatchException("Error reading input: cannot be null or blank.");
        }

        return input.trim();
    }

    public ChessPosition readChessPosition() {
        String input = readInput();

        if (!input.matches("[a-h][1-8]")) {
            throw new InputMismatchException(
                    "Error reading ChessPosition. Valid values are from a1 to h8.");
        }

        char column = input.charAt(0);
        int row = Character.getNumericValue(input.charAt(1));

        return new ChessPosition(column, row);
    }

    public ChessPiece.Name readPromotedPiece() {
        String input = readInput().substring(0, 1);

        return ChessMatch.possiblePromotedPieces
                .stream()
                .filter(pieceName -> pieceName.getLetter().equalsIgnoreCase(input))
                .findFirst()
                .orElse(null);
    }
}
