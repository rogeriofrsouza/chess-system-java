package com.rogeriofrsouza.app.boardgame;

import com.rogeriofrsouza.app.chess.ChessPosition;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Position {

    private int row;
    private int column;

    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public ChessPosition toChessPosition() {
        return new ChessPosition((char) (column + 'a'), 8 - row);
    }
}
