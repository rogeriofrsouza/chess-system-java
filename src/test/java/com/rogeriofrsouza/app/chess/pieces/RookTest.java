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
class RookTest {

    @Test
    void shouldComputePossibleMovesForRook() {
        Board board = new Board();
        Rook rook = new Rook(board, ChessPiece.Color.WHITE);

        board.placePiece(rook, new ChessPosition('b', 3).toPosition());
        board.placePiece(new Knight(board, ChessPiece.Color.WHITE), new ChessPosition('b', 1).toPosition());
        board.placePiece(new Bishop(board, ChessPiece.Color.BLACK), new ChessPosition('b', 7).toPosition());

        rook.computePossibleMoves();
        assertTrue(
                Arrays.stream(board.getSquares())
                        .flatMap(Arrays::stream)
                        .anyMatch(BoardSquare::isPossibleMove));
    }
}
