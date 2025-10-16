package com.rogeriofrsouza.app.chess;

import com.rogeriofrsouza.app.boardgame.Position;

public record ChessPosition(char column, int row) {

    public ChessPosition {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new IllegalArgumentException("Invalid chess position. Should be a1 - h8");
        }
    }

    public Position toPosition() {
        return new Position(8 - row, column - 'a');
    }
}
