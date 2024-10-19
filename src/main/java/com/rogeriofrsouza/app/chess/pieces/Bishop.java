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

        // north-west
        targetPosition.setValues(position.getRow() - 1, position.getColumn() - 1);

        while (getBoard().positionExists(targetPosition) && !getBoard().thereIsAPiece(targetPosition)) {
            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
            targetPosition.setValues(targetPosition.getRow() - 1, targetPosition.getColumn() - 1);
        }

        if (getBoard().positionExists(targetPosition) && isThereOpponentPiece(targetPosition)) {
            possibleMoves[targetPosition.getRow()][targetPosition.getColumn()] = true;
        }

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
}
