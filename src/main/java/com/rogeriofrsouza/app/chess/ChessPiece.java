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

    private Name name;
    private Color color;
    private int moveCount;

    protected ChessPiece(Board board, Color color) {
        super(board);
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
        ChessPiece p = (ChessPiece) getBoard().piece(position); // Downcasting

        return p != null && p.getColor() != color;
    }

    protected void increaseMoveCount() {
        moveCount++;
    }

    protected void decreaseMoveCount() {
        moveCount--;
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

        private String letter;
    }
}
