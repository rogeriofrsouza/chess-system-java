package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.chess.ChessMoveDirection;
import com.rogeriofrsouza.app.chess.ChessPiece;

import java.util.List;

public class Queen extends ChessPiece {

    private static final List<ChessMoveDirection> CHESS_MOVE_DIRECTIONS = List.of(
        ChessMoveDirection.UP, ChessMoveDirection.RIGHT,
        ChessMoveDirection.DOWN, ChessMoveDirection.LEFT,
        ChessMoveDirection.UP_LEFT, ChessMoveDirection.UP_RIGHT,
        ChessMoveDirection.DOWN_RIGHT, ChessMoveDirection.DOWN_LEFT);

    public Queen(Board board, Color color) {
        super(board, Name.QUEEN, color);
    }

    @Override
    public boolean[][] computePossibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        CHESS_MOVE_DIRECTIONS.forEach(direction -> checkMoves(possibleMoves, direction));

        return possibleMoves;
    }
}
