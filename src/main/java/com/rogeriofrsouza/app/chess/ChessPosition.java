package com.rogeriofrsouza.app.chess;

import com.rogeriofrsouza.app.boardgame.Position;
import lombok.Getter;

@Getter
public class ChessPosition {

    private final char column;
    private final int row;

    public ChessPosition(char column, int row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new IllegalArgumentException("Invalid chess position. Should be a1 - h8");
        }

        this.column = column;
        this.row = row;
    }

    public Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
