package com.rogeriofrsouza.app.chess;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Piece;
import com.rogeriofrsouza.app.boardgame.Position;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class ChessPiece extends Piece {

    private final Name name;
    private final Color color;
    private int moveCount;

    protected ChessPiece(Board board, Color color) {
        super(board);
        this.name = null;
        this.color = color;
    }

    protected ChessPiece(Board board, Name name, Color color) {
        super(board);
        this.name = name;
        this.color = color;
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

    protected void checkMoves(boolean[][] possibleMoves, ChessMoveDirection direction) {
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

    public enum Color {
        BLACK,
        WHITE
    }
}
