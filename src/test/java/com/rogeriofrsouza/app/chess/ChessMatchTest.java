package com.rogeriofrsouza.app.chess;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.pieces.Rook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ChessMatchTest {

    @InjectMocks
    private ChessMatch chessMatchMock;

    @Mock
    private Board boardMock;

    @Test()
    @DisplayName("should place pieces on the board")
    void chessMatchInitialSetup() {
        ChessMatch chessMatch = new ChessMatch();

        assertEquals(1, chessMatch.getTurn());
        assertEquals(ChessPiece.Color.WHITE, chessMatch.getCurrentPlayer());
    }

    @Test
    @DisplayName("should throw ChessException, the chosen piece is not yours")
    void possibleMoves_chosenPieceNotYours_throwChessException() {
        ChessPosition chessPosition = new ChessPosition('a', 4);
        Position position = new Position(4, 0);
        Rook chessPiece = new Rook(new Board(), ChessPiece.Color.BLACK);

        doReturn(chessPiece).when(boardMock).getPieceAt(position);

        assertThrowsExactly(
                ChessException.class,
                () -> chessMatchMock.computePossibleMoves(chessPosition));
    }

    @Test
    @DisplayName("should throw ChessException, there is no piece on source position")
    void possibleMoves_noPieceOnPosition_throwChessException() {
        ChessPosition chessPosition = new ChessPosition('a', 4);

        assertThrowsExactly(
                ChessException.class,
                () -> chessMatchMock.computePossibleMoves(chessPosition));
    }
}
