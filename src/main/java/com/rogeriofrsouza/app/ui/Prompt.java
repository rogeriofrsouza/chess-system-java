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
            throw new InputMismatchException("Input cannot be null or blank");
        }

        return input.trim();
    }

    public ChessPosition readChessPosition() {
        String input = readInput();

        if (input.length() < 2) {
            throw new InputMismatchException("Expected two characters");
        }

        char column = input.charAt(0);
        int row = Character.getNumericValue(input.charAt(1));

        return new ChessPosition(column, row);
    }

    public ChessPiece.Name readPromotedPiece() {
        String input = readInput();

        return ChessMatch.possiblePromotedPieces
                .stream()
                .filter(n -> n.getLetter().equalsIgnoreCase(input))
                .findFirst()
                .orElse(null);
    }
}
