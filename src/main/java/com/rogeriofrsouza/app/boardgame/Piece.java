package com.rogeriofrsouza.app.boardgame;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class Piece {

    protected Position position;

    private Board board;

    protected Piece(Board board) {
        this.board = board;
    }

    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] computePossibleMoves();

    public boolean isTargetPossibleMove(Position target) {
        return computePossibleMoves()[target.getRow()][target.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] possibleMoves = computePossibleMoves();

        for (int i = 0; i < possibleMoves.length; i++) {
            for (int j = 0; j < possibleMoves[i].length; j++) {
                if (possibleMoves[i][j]) {
                    return true;
                }
            }
        }

        return false;
    }
}
