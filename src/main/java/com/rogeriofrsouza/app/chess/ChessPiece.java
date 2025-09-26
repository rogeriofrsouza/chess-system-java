package com.rogeriofrsouza.app.chess;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Piece;
import com.rogeriofrsouza.app.boardgame.Position;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class ChessPiece extends Piece {

    private final Name name;
    private final Color color;
    private int moveCount;
    private List<ChessMoveDirection> chessMoveDirections;

    protected ChessPiece(Board board, Color color) {
        super(board);
        this.name = null;
        this.color = color;
    }

    protected ChessPiece(
        Board board, Color color, Name name, List<ChessMoveDirection> chessMoveDirections) {
        super(board);
        this.color = color;
        this.name = name;

        if (chessMoveDirections == null || chessMoveDirections.isEmpty()) {
            throw new IllegalArgumentException("Invalid chess move directions");
        }
        this.chessMoveDirections = chessMoveDirections;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);

        return piece != null && piece.getColor() != color;
    }

    protected void increaseMoveCount() {
        moveCount++;
    }

    protected void decreaseMoveCount() {
        moveCount--;
    }

    @Override
    public String toString() {
        return getName().getLetter();
    }

    @Override
    public boolean[][] computePossibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        for (ChessMoveDirection direction : getChessMoveDirections()) {
            Position targetPosition = new Position(position.getRow(), position.getColumn());

            while (true) {
                changeTargetPosition(targetPosition, direction);

                if (!getBoard().positionExists(targetPosition)) {
                    break;
                }

                if (getBoard().thereIsAPiece(targetPosition)) {
                    if (isThereOpponentPiece(targetPosition)) {
                        getBoard().makeSquarePossibleMove(targetPosition);
                    }
                    break;
                }

                getBoard().makeSquarePossibleMove(targetPosition);
            }
        }

        return possibleMoves;
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

    @AllArgsConstructor
    @Getter
    public enum Name {

        ROOK("R"),
        KNIGHT("N"),
        BISHOP("B"),
        QUEEN("Q"),
        KING("K"),
        PAWN("P");

        private final String letter;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Color {

        BLACK("Black"),
        WHITE("White");

        private final String value;
    }
}
