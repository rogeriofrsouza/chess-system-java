package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch; // Dependência para a partida

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch; // Associação entre os objetos
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] computePossibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // White pawn
        if (getColor() == Color.WHITE) {
            p.setValues(position.getRow() - 1, position.getColumn());

            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                getBoard().makeSquarePossibleMove(p);
            }

            Position p2 = p;
            p.setValues(position.getRow() - 2, position.getColumn());

            if (getBoard().positionExists(p)
                    && !getBoard().thereIsAPiece(p)
                    && getBoard().positionExists(p2)
                    && !getBoard().thereIsAPiece(p2)
                    && getMoveCount() == 0) {
                getBoard().makeSquarePossibleMove(p);
            }

            p.setValues(position.getRow() - 1, position.getColumn() - 1);

            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                getBoard().makeSquarePossibleMove(p);
            }

            p.setValues(position.getRow() - 1, position.getColumn() + 1);

            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                getBoard().makeSquarePossibleMove(p);
            }

            // Special move: En Passant
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);

                if (getBoard().positionExists(left)
                        && isThereOpponentPiece(left)
                        && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                    p.setValues(left.getRow() - 1, left.getColumn());
                    getBoard().makeSquarePossibleMove(p);
                }

                Position right = new Position(position.getRow(), position.getColumn() + 1);

                if (getBoard().positionExists(right)
                        && isThereOpponentPiece(right)
                        && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                    p.setValues(right.getRow() - 1, right.getColumn());
                    getBoard().makeSquarePossibleMove(p);
                }
            }
        }
        // Black pawn
        else {
            p.setValues(position.getRow() + 1, position.getColumn());

            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                getBoard().makeSquarePossibleMove(p);
            }

            Position p2 = p;
            p.setValues(position.getRow() + 2, position.getColumn());

            if (getBoard().positionExists(p)
                    && !getBoard().thereIsAPiece(p)
                    && getBoard().positionExists(p2)
                    && !getBoard().thereIsAPiece(p2)
                    && getMoveCount() == 0) {
                getBoard().makeSquarePossibleMove(p);
            }

            p.setValues(position.getRow() + 1, position.getColumn() - 1);

            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                getBoard().makeSquarePossibleMove(p);
            }

            p.setValues(position.getRow() + 1, position.getColumn() + 1);

            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                getBoard().makeSquarePossibleMove(p);
            }

            // Special move: En Passant
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);

                if (getBoard().positionExists(left)
                        && isThereOpponentPiece(left)
                        && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                    p.setValues(left.getRow() + 1, left.getColumn());
                    getBoard().makeSquarePossibleMove(p);
                }

                Position right = new Position(position.getRow(), position.getColumn() + 1);

                if (getBoard().positionExists(right)
                        && isThereOpponentPiece(right)
                        && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                    p.setValues(right.getRow() + 1, right.getColumn());
                    getBoard().makeSquarePossibleMove(p);
                }
            }
        }

        return mat;
    }
}
