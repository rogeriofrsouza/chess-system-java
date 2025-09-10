package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;

public class King extends ChessPiece {

    private ChessMatch chessMatch; // Dependência para a partida

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch; // Associação entre os objetos
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] computePossibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // above
        p.setValues(position.getRow() - 1, position.getColumn());

        if (getBoard().positionExists(p) && canMove(p)) {
            getBoard().makeSquarePossibleMove(p);
        }

        // below
        p.setValues(position.getRow() + 1, position.getColumn());

        if (getBoard().positionExists(p) && canMove(p)) {
            getBoard().makeSquarePossibleMove(p);
        }

        // left
        p.setValues(position.getRow(), position.getColumn() - 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            getBoard().makeSquarePossibleMove(p);
        }

        // right
        p.setValues(position.getRow(), position.getColumn() + 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            getBoard().makeSquarePossibleMove(p);
        }

        // north-west
        p.setValues(position.getRow() - 1, position.getColumn() - 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            getBoard().makeSquarePossibleMove(p);
        }

        // north-east
        p.setValues(position.getRow() - 1, position.getColumn() + 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            getBoard().makeSquarePossibleMove(p);
        }

        // south-west
        p.setValues(position.getRow() + 1, position.getColumn() - 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            getBoard().makeSquarePossibleMove(p);
        }

        // south-east
        p.setValues(position.getRow() + 1, position.getColumn() + 1);

        if (getBoard().positionExists(p) && canMove(p)) {
            getBoard().makeSquarePossibleMove(p);
        }

        // Special move: Castling
        if (getMoveCount() == 0 && !chessMatch.isCheck()) {

            // Kingside rook
            Position posT1 = new Position(position.getRow(), position.getColumn() + 3);

            if (testRookCastling(posT1)) {
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);

                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    p.setValues(position.getRow(), position.getColumn() + 2);
                    getBoard().makeSquarePossibleMove(p);
                }
            }

            // Queenside rook
            Position posT2 = new Position(position.getRow(), position.getColumn() - 4);

            if (testRookCastling(posT2)) {
                Position p1 = new Position(position.getRow(), position.getColumn() - 1);
                Position p2 = new Position(position.getRow(), position.getColumn() - 2);
                Position p3 = new Position(position.getRow(), position.getColumn() - 3);

                if (getBoard().piece(p1) == null
                        && getBoard().piece(p2) == null
                        && getBoard().piece(p3) == null) {
                    p.setValues(position.getRow(), position.getColumn() - 2);
                    getBoard().makeSquarePossibleMove(p);
                }
            }
        }

        return mat;
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);

        return p == null || p.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);

        return p != null
                && p instanceof Rook
                && p.getColor() == getColor()
                && p.getMoveCount() == 0;
    }
}
