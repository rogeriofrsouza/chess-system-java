package com.rogeriofrsouza.app.boardgame;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Getter
public class Board {

    private final int rows;
    private final int columns;

    private final BoardSquare[][] squares;

    public Board() {
        this.rows = 8;
        this.columns = 8;

        squares = new BoardSquare[rows][columns];
        IntStream.range(0, rows)
                .forEach(r -> Arrays.setAll(squares[r], c -> new BoardSquare(new Position(r, c))));
    }

    public Piece getPieceAt(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }

        return squares[position.getRow()][position.getColumn()].getPiece();
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }

        squares[position.getRow()][position.getColumn()].setPiece(piece);
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        Piece piece = getPieceAt(position);

        if (piece != null) {
            squares[position.getRow()][position.getColumn()].setPiece(null);
            piece.position = null;
        }

        return piece;
    }

    public boolean positionExists(Position position) {
        int row = position.getRow();
        int column = position.getColumn();

        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean thereIsAPiece(Position position) {
        return getPieceAt(position) != null;
    }

    public List<Piece> getPiecesOnTheBoard() {
        return Arrays.stream(squares)
                .flatMap(Arrays::stream)
                .map(BoardSquare::getPiece)
                .filter(Objects::nonNull)
                .toList();
    }

    public void makeSquarePossibleMove(Position position) {
        squares[position.getRow()][position.getColumn()].setPossibleMove(true);
    }
}
