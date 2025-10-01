package com.rogeriofrsouza.app.boardgame;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class Piece {

    protected Position position;
    private final Board board;

    protected Piece(Board board) {
        this.board = board;
    }

    public abstract boolean[][] computePossibleMoves();
}
