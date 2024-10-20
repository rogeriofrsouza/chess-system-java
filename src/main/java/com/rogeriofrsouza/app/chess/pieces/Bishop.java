package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.chess.ChessMoveDirection;
import com.rogeriofrsouza.app.chess.ChessPiece;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, Name.BISHOP, color);
    }

    @Override
    public boolean[][] computePossibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        checkMoves(possibleMoves, ChessMoveDirection.UP_LEFT);
        checkMoves(possibleMoves, ChessMoveDirection.UP_RIGHT);
        checkMoves(possibleMoves, ChessMoveDirection.DOWN_LEFT);
        checkMoves(possibleMoves, ChessMoveDirection.DOWN_RIGHT);

        return possibleMoves;
    }
}
