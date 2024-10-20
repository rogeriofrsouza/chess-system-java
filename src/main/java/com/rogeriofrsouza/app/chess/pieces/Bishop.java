package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Position;
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

    private void checkMoves(boolean[][] possibleMoves, ChessMoveDirection direction) {
        Position targetPosition = new Position(position.getRow(), position.getColumn());

        while (true) {
            changeTargetPosition(targetPosition, direction);

            if (!getBoard().positionExists(targetPosition)) {
                return;
            }

            if (getBoard().thereIsAPiece(targetPosition)) {
                possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] =
                    isThereOpponentPiece(targetPosition);
                return;
            }

            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
        }
    }

    private void changeTargetPosition(Position targetPosition, ChessMoveDirection direction) {
        switch (direction) {
            case UP -> targetPosition.setRow(targetPosition.getRow() - 1);
            case DOWN -> targetPosition.setRow(targetPosition.getRow() + 1);
            case LEFT -> targetPosition.setColumn(targetPosition.getColumn() - 1);
            case RIGHT -> targetPosition.setColumn(targetPosition.getColumn() + 1);
            case UP_LEFT -> targetPosition.setValues(
                targetPosition.getRow() - 1, targetPosition.getColumn() - 1);
            case UP_RIGHT -> targetPosition.setValues(
                targetPosition.getRow() - 1, targetPosition.getColumn() + 1);
            case DOWN_LEFT -> targetPosition.setValues(
                targetPosition.getRow() + 1, targetPosition.getColumn() - 1);
            case DOWN_RIGHT -> targetPosition.setValues(
                targetPosition.getRow() + 1, targetPosition.getColumn() + 1);
            default -> throw new IllegalArgumentException("Invalid direction");
        }
    }
}
