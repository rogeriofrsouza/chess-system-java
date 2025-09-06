package com.rogeriofrsouza.app.boardgame;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.IntStream;

@Getter
public class Board {

    private final int rows;
    private final int columns;

    private final BoardSquare[][] squares;
    private final Piece[][] pieces;

    public Board() {
        this.rows = 8;
        this.columns = 8;

        squares = new BoardSquare[rows][columns];
        IntStream.range(0, rows)
                .forEach(r -> Arrays.setAll(squares[r], c -> new BoardSquare(new Position(r, c))));

        pieces = new Piece[rows][columns];
    }

    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardException("Position not on the board");
        }

        return pieces[row][column];
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }

        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }

        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }

        if (piece(position) == null) {
            return null;
        }

        Piece aux = piece(position);
        aux.position = null; // Peça retirada do tabuleiro, não possui posição

        pieces[position.getRow()][position.getColumn()] = null;

        return aux;
    }

    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }

        return piece(position) != null;
    }
}
