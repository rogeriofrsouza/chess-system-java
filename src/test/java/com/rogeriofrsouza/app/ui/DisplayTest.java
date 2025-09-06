package com.rogeriofrsouza.app.ui;

import com.rogeriofrsouza.app.boardgame.Board;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

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

        Board board = new Board(8, 8);
        List<ChessPiece> captured = List.of(
            new Rook(board, ChessPiece.Color.WHITE), new Rook(board, ChessPiece.Color.WHITE),
            new Rook(board, ChessPiece.Color.BLACK), new Rook(board, ChessPiece.Color.BLACK));

        String outputExpected = "%nCaptured pieces%nWhite: %s%s%n%sBlack: %s%s%n%s".formatted(
            WHITE,
            captured.subList(0, 2),
            RESET,
            YELLOW,
            captured.subList(2, 4),
            RESET) +
            "\nTurn: " + chessMatch.getTurn() + "\n" +
            "Waiting player: " + chessMatch.getCurrentPlayer() + "\n";

        doNothing().when(display).printBoard(any(ChessPiece[][].class), any());
        display.printMatch(chessMatch, captured);

        assertEquals(outputExpected, outputStream.toString());
        verify(display).printBoard(any(ChessPiece[][].class), any());
    }

    @Test
    @DisplayName("should print the match information and CHECK")
    void printMatch_isCheck_logMatchAndCheck() {
        ChessMatch chessMatch = new ChessMatch();
        chessMatch.setCheck(true);
        chessMatch.setTurn(50);
        chessMatch.setCurrentPlayer(ChessPiece.Color.BLACK);

        Board board = new Board(8, 8);
        List<ChessPiece> captured = List.of(
            new Rook(board, ChessPiece.Color.WHITE), new Rook(board, ChessPiece.Color.WHITE));

        String outputExpected = "%nCaptured pieces%nWhite: %s%s%n%sBlack: %s%s%n%s".formatted(
            WHITE,
            captured,
            RESET,
            YELLOW,
            List.of(),
            RESET) +
            "\nTurn: " + chessMatch.getTurn() + "\n" +
            "Waiting player: " + chessMatch.getCurrentPlayer() + "\n" +
            "CHECK!\n";

        doNothing().when(display).printBoard(any(ChessPiece[][].class), any());
        display.printMatch(chessMatch, captured);

        assertEquals(outputExpected, outputStream.toString());
        verify(display).printBoard(any(ChessPiece[][].class), any());
    }

    @Test
    @DisplayName("should print the match information, CHECKMATE and the winner")
    void printMatch_isCheckMate_logMatchAndCheckMate() {
        ChessMatch chessMatch = new ChessMatch();
        chessMatch.setCheckMate(true);
        chessMatch.setTurn(10);
        chessMatch.setCurrentPlayer(ChessPiece.Color.BLACK);

        Board board = new Board(8, 8);
        List<ChessPiece> captured = List.of(
            new Rook(board, ChessPiece.Color.BLACK), new Rook(board, ChessPiece.Color.BLACK));

        String stringBuilder = "%nCaptured pieces%nWhite: %s%s%n%sBlack: %s%s%n%s".formatted(
            WHITE,
            List.of(),
            RESET,
            YELLOW,
            captured,
            RESET) +
            "\nTurn: " + chessMatch.getTurn() + "\n" +
            "CHECKMATE!\nWinner: " + chessMatch.getCurrentPlayer() + "\n";

        doNothing().when(display).printBoard(any(ChessPiece[][].class), any());
        display.printMatch(chessMatch, captured);

        assertEquals(stringBuilder, outputStream.toString());
        verify(display).printBoard(any(ChessPiece[][].class), any());
    }

    @Test
    @DisplayName("should print the board, pieces and its corresponding colors")
    void printBoard_noPossibleMove_printBoardPiecesColors() {
        Board board = new Board(4, 1);

        ChessPiece[][] pieces = new ChessPiece[][]{
            {new Rook(board, ChessPiece.Color.BLACK)},
            {null}, {null},
            {new Rook(board, ChessPiece.Color.WHITE)}
        };

        String stringExpected = "8 %sR%s %n7 -%s %n6 -%s %n5 %sR%s %n  a b c d e f g h%n".formatted(
            YELLOW, RESET, RESET,
            RESET, WHITE, RESET);

        display.printBoard(pieces, null);
        assertEquals(stringExpected, outputStream.toString());
    }

    @Test
    @DisplayName("should print the board, pieces, its corresponding colors and possibleMoves")
    void printBoard_hasPossibleMoves_printBoardPiecesColorsMoves() {
        Board board = new Board(4, 1);

        ChessPiece[][] pieces = new ChessPiece[][]{
            {new Rook(board, ChessPiece.Color.BLACK)},
            {null}, {null},
            {new Rook(board, ChessPiece.Color.WHITE)}
        };

        boolean[][] possibleMoves = new boolean[][]{{true}, {true}, {false}, {false}};

        String stringExpected = "8 %s%sR%s %n7 %s-%s %n6 -%s %n5 %sR%s %n  a b c d e f g h%n".formatted(
            BLUE_BACKGROUND, YELLOW, RESET,
            BLUE_BACKGROUND, RESET, RESET,
            WHITE, RESET);

        display.printBoard(pieces, possibleMoves);
        assertEquals(stringExpected, outputStream.toString());
    }
}
