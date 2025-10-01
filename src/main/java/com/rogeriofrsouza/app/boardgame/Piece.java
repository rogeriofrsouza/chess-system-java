package com.rogeriofrsouza.app.boardgame;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
@EqualsAndHashCode
public abstract class Piece {

    protected Position position;
    private final Board board;

    protected Piece(Board board) {
        this.board = board;
    }

    public abstract boolean[][] computePossibleMoves();

    public boolean isThereAnyPossibleMove() {
        return Arrays.stream(getBoard().getSquares())
                .flatMap(Arrays::stream)
                .anyMatch(BoardSquare::isPossibleMove);
    }
}
