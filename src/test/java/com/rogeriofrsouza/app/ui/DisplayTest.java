package com.rogeriofrsouza.app.ui;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Piece;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.pieces.Rook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static com.rogeriofrsouza.app.ui.AnsiEscapeCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class DisplayTest {

    @InjectMocks
    @Spy
    private Display display;

    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream outputStream;

    private final InputStream systemIn = System.in;
    private ByteArrayInputStream inputStream;

    @BeforeEach
    void setUpOutput() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreSystemInputOutput() {
        System.setOut(systemOut);
        System.setIn(systemIn);
    }

    private void provideInput(String data) {
        inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
    }

    @Test
    @DisplayName("should clear the console")
    void clearScreen() {
        display.clearScreen();
        String expected = "%s%s".formatted(MOVE_CURSOR_HOME, CLEAR_SCREEN);
        assertEquals(expected, outputStream.toString());
    }

    @Test
    @DisplayName("should print the match information and status")
    void printMatch_notCheckNotCheckmate_logMatch() {
        ChessMatch chessMatch = new ChessMatch();

        Board board = chessMatch.getBoard();
        List<Piece> captured = List.of(
                new Rook(board, ChessPiece.Color.WHITE), new Rook(board, ChessPiece.Color.WHITE),
                new Rook(board, ChessPiece.Color.BLACK), new Rook(board, ChessPiece.Color.BLACK));
        chessMatch.setCapturedPieces(captured);

        String outputExpected = "Captured pieces%nBlack: %s%s%s%nWhite: %s%s%s%n".formatted(
                YELLOW,
                captured.subList(2, 4),
                RESET,
                WHITE,
                captured.subList(0, 2),
                RESET) +
                "Turn: " + chessMatch.getTurn() + "\n" +
                "Waiting player: " + chessMatch.getCurrentPlayer() + "\n";

        doNothing().when(display).printBoard(board);
        display.printMatch(chessMatch);

        assertEquals(outputExpected, outputStream.toString());
    }

    @Test
    @DisplayName("should print the match information and CHECK")
    void printMatch_isCheck_logMatchAndCheck() {
        ChessMatch chessMatch = new ChessMatch();
        chessMatch.setCheck(true);
        chessMatch.setTurn(50);
        chessMatch.setCurrentPlayer(ChessPiece.Color.BLACK);

        Board board = chessMatch.getBoard();
        List<Piece> captured = List.of(
                new Rook(board, ChessPiece.Color.WHITE), new Rook(board, ChessPiece.Color.WHITE));
        chessMatch.setCapturedPieces(captured);

        String outputExpected = "Captured pieces%nBlack: %s%s%s%nWhite: %s%s%s%n".formatted(
                YELLOW,
                List.of(),
                RESET,
                WHITE,
                captured,
                RESET) +
                "Turn: " + chessMatch.getTurn() + "\n" +
                "Waiting player: " + chessMatch.getCurrentPlayer() + "\n" +
                "CHECK!\n";

        doNothing().when(display).printBoard(board);
        display.printMatch(chessMatch);

        assertEquals(outputExpected, outputStream.toString());
    }

    @Test
    @DisplayName("should print the match information, CHECKMATE and the winner")
    void printMatch_isCheckMate_logMatchAndCheckMate() {
        ChessMatch chessMatch = new ChessMatch();
        chessMatch.setCheckMate(true);
        chessMatch.setTurn(10);
        chessMatch.setCurrentPlayer(ChessPiece.Color.BLACK);

        Board board = chessMatch.getBoard();
        List<Piece> captured = List.of(
                new Rook(board, ChessPiece.Color.BLACK), new Rook(board, ChessPiece.Color.BLACK));
        chessMatch.setCapturedPieces(captured);

        String stringBuilder = "Captured pieces%nBlack: %s%s%s%nWhite: %s%s%s%n".formatted(
                YELLOW,
                captured,
                RESET,
                WHITE,
                List.of(),
                RESET) +
                "Turn: " + chessMatch.getTurn() + "\n" +
                "CHECKMATE!\nWinner: " + chessMatch.getCurrentPlayer() + "\n";

        doNothing().when(display).printBoard(board);
        display.printMatch(chessMatch);

        assertEquals(stringBuilder, outputStream.toString());
    }
}
