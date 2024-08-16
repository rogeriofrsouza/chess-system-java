package com.rogeriofrsouza.app.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Piece;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.pieces.Rook;

@ExtendWith(MockitoExtension.class)
class ChessMatchTest {

    @InjectMocks
    private ChessMatch chessMatchMock;

    @Mock
    private Board boardMock;

    @Mock
    private Piece pieceMock;

    @Mock
    private ChessPiece chessPieceMock;

    @Mock
    private ChessPosition chessPositionMock;

    @Test()
    @DisplayName("should place pieces on the board")
    void chessMatchInitialSetup() {
        ChessMatch chessMatch = new ChessMatch();

        assertEquals(1, chessMatch.getTurn());
        assertEquals(Color.WHITE, chessMatch.getCurrentPlayer());
        assertEquals(32, chessMatch.getPiecesOnTheBoard().size());
    }

    @Test
    @DisplayName("should validate the source position and return possible moves")
    void possibleMoves_validatePosition_returnPossibleMoves() {
        Rook chessPiece = new Rook(new Board(3, 3), Color.WHITE);
        boolean[][] possibleMovesExpected = new boolean[][] {{true, true, false}};

        doReturn(true).when(boardMock).thereIsAPiece(any(Position.class));

        doReturn(chessPiece).doReturn(pieceMock).doReturn(pieceMock)
                .when(boardMock).piece(any(Position.class));

        doReturn(true).when(pieceMock).isThereAnyPossibleMove();
        doReturn(possibleMovesExpected).when(pieceMock).possibleMoves();

        assertEquals(
                possibleMovesExpected,
                chessMatchMock.computePossibleMoves(new ChessPosition('a', 4)));

        verify(pieceMock).possibleMoves();
    }

    @Test
    @DisplayName("should throw ChessException, there is no possible moves")
    void possibleMoves_noPossibleMoves_throwChessException() {
        Rook chessPiece = new Rook(new Board(3, 3), Color.WHITE);

        doReturn(true).when(boardMock).thereIsAPiece(any(Position.class));
        doReturn(chessPiece).doReturn(pieceMock).when(boardMock).piece(any(Position.class));
        doReturn(false).when(pieceMock).isThereAnyPossibleMove();

        assertThrowsExactly(
                ChessException.class,
                () -> chessMatchMock.computePossibleMoves(new ChessPosition('a', 4)));
    }

    @Test
    @DisplayName("should throw ChessException, the chosen piece is not yours")
    void possibleMoves_chosenPieceNotYours_throwChessException() {
        Rook chessPiece = new Rook(new Board(3, 3), Color.BLACK);

        doReturn(true).when(boardMock).thereIsAPiece(any(Position.class));
        doReturn(chessPiece).when(boardMock).piece(any(Position.class));

        assertThrowsExactly(
                ChessException.class,
                () -> chessMatchMock.computePossibleMoves(new ChessPosition('a', 4)));
    }

    @Test
    @DisplayName("should throw ChessException, there is no piece on source position")
    void possibleMoves_noPieceOnPosition_throwChessException() {
        doReturn(false).when(boardMock).thereIsAPiece(any(Position.class));

        assertThrowsExactly(
                ChessException.class,
                () -> chessMatchMock.computePossibleMoves(new ChessPosition('a', 4)));
    }
}
