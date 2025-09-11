package com.rogeriofrsouza.app.chess.pieces;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.BoardSquare;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.ChessPosition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BishopTest {

    @Test
    void shouldComputePossibleMovesForBishop() {
        Board board = new Board();
        Bishop bishop = new Bishop(board, ChessPiece.Color.WHITE);

        board.placePiece(bishop, new ChessPosition('b', 2).toPosition());
        board.placePiece(new Rook(board, ChessPiece.Color.WHITE), new ChessPosition('a', 1).toPosition());
        board.placePiece(new Knight(board, ChessPiece.Color.BLACK), new ChessPosition('f', 6).toPosition());

        bishop.computePossibleMoves();

        assertTrue(
                Arrays.stream(board.getSquares())
                        .flatMap(Arrays::stream)
                        .anyMatch(BoardSquare::isPossibleMove));
    }
}
