package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.ChessPiece;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, Name.BISHOP, color);
    }

    @Override
    public boolean[][] computePossibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position targetPosition = new Position(0, 0);

        northWestMoves(possibleMoves);

        // north-east
        targetPosition.setValues(position.getRow() - 1, position.getColumn() + 1);

        while (getBoard().positionExists(targetPosition) && !getBoard().thereIsAPiece(targetPosition)) {
            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
            targetPosition.setValues(targetPosition.getRow() - 1, targetPosition.getColumn() + 1);
        }

        if (getBoard().positionExists(targetPosition) && isThereOpponentPiece(targetPosition)) {
            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
        }

        // south-east
        targetPosition.setValues(position.getRow() + 1, position.getColumn() + 1);

        while (getBoard().positionExists(targetPosition) && !getBoard().thereIsAPiece(targetPosition)) {
            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
            targetPosition.setValues(targetPosition.getRow() + 1, targetPosition.getColumn() + 1);
        }

        if (getBoard().positionExists(targetPosition) && isThereOpponentPiece(targetPosition)) {
            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
        }

        // south-west
        targetPosition.setValues(position.getRow() + 1, position.getColumn() - 1);

        while (getBoard().positionExists(targetPosition) && !getBoard().thereIsAPiece(targetPosition)) {
            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
            targetPosition.setValues(targetPosition.getRow() + 1, targetPosition.getColumn() - 1);
        }

        if (getBoard().positionExists(targetPosition) && isThereOpponentPiece(targetPosition)) {
            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
        }

        return possibleMoves;
    }

    private void northWestMoves(boolean[][] possibleMoves) {
        Position targetPosition = new Position(position.getRow() - 1, position.getColumn() - 1);

        if (!getBoard().positionExists(targetPosition)) {
            return;
        }

        if (isThereOpponentPiece(targetPosition)) {
            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
            return;
        }

        while (!getBoard().thereIsAPiece(targetPosition)) {
            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
            targetPosition.setValues(targetPosition.getRow() - 1, targetPosition.getColumn() - 1);
        }
    }
}
