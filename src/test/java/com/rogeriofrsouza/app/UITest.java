package com.rogeriofrsouza.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;
import com.rogeriofrsouza.app.chess.Color;
import com.rogeriofrsouza.app.chess.pieces.Rook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UITest {

    @InjectMocks @Spy private UI ui;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("should clear and flush the console")
    void clearScreen() {
        ui.clearScreen();

        String expected = UI.ANSI_MOVE_CURSOR_HOME + UI.ANSI_CLEAR_SCREEN;
        assertEquals(expected, outputStreamCaptor.toString());
    }

    @Test
    @DisplayName("should print the match information and status")
    void printMatch_notCheckNotCheckmate_logMatch() {
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(
                        String.format(
                                "%nCaptured pieces%nWhite: %s%s%n%sBlack: %s%s%n%s",
                                UI.ANSI_WHITE,
                                captured,
                                UI.ANSI_RESET,
                                UI.ANSI_YELLOW,
                                captured,
                                UI.ANSI_RESET))
                .append("\nTurn: " + chessMatch.getTurn() + "\n")
                .append("Waiting player: " + chessMatch.getCurrentPlayer() + "\n");

        doNothing().when(ui).printBoard(any(ChessPiece[][].class), any());

        ui.printMatch(chessMatch, captured);

        assertEquals(stringBuilder.toString(), outputStreamCaptor.toString());
        verify(ui).printBoard(any(ChessPiece[][].class), any());
    }

    @Test
    @DisplayName("should print the match information and CHECK")
    void printMatch_isCheck_logMatchAndCheck() {
        ChessMatch chessMatch = new ChessMatch();
        chessMatch.setCheck(true);
        chessMatch.setTurn(50);
        chessMatch.setCurrentPlayer(Color.BLACK);

        List<ChessPiece> captured = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(
                        String.format(
                                "%nCaptured pieces%nWhite: %s%s%n%sBlack: %s%s%n%s",
                                UI.ANSI_WHITE,
                                captured,
                                UI.ANSI_RESET,
                                UI.ANSI_YELLOW,
                                captured,
                                UI.ANSI_RESET))
                .append("\nTurn: " + chessMatch.getTurn() + "\n")
                .append("Waiting player: " + chessMatch.getCurrentPlayer() + "\n")
                .append("CHECK!\n");

        doNothing().when(ui).printBoard(any(ChessPiece[][].class), any());

        ui.printMatch(chessMatch, captured);

        assertEquals(stringBuilder.toString(), outputStreamCaptor.toString());
        verify(ui).printBoard(any(ChessPiece[][].class), any());
    }

    @Test
    @DisplayName("should print the match information, CHECKMATE and the winner")
    void printMatch_isCheckMate_logMatchAndCheckMate() {
        ChessMatch chessMatch = new ChessMatch();
        chessMatch.setCheckMate(true);
        chessMatch.setTurn(10);
        chessMatch.setCurrentPlayer(Color.BLACK);

        List<ChessPiece> captured = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(
                        String.format(
                                "%nCaptured pieces%nWhite: %s%s%n%sBlack: %s%s%n%s",
                                UI.ANSI_WHITE,
                                captured,
                                UI.ANSI_RESET,
                                UI.ANSI_YELLOW,
                                captured,
                                UI.ANSI_RESET))
                .append("\nTurn: " + chessMatch.getTurn() + "\n")
                .append("CHECKMATE!\nWinner: " + chessMatch.getCurrentPlayer() + "\n");

        doNothing().when(ui).printBoard(any(ChessPiece[][].class), any());

        ui.printMatch(chessMatch, captured);

        assertEquals(stringBuilder.toString(), outputStreamCaptor.toString());
        verify(ui).printBoard(any(ChessPiece[][].class), any());
    }

    @Test
    @DisplayName("should print the board, pieces and its corresponding colors")
    void printBoard_noPossibleMove_printBoardPiecesColors() {
        Board board = new Board(4, 1);

        ChessPiece[][] pieces =
                new ChessPiece[][] {
                    {new Rook(board, Color.BLACK)}, {null}, {null}, {new Rook(board, Color.WHITE)}
                };

        String stringExpected =
                String.format(
                        "8 %sR%s %n7 -%s %n6 -%s %n5 %sR%s %n  a b c d e f g h%n",
                        UI.ANSI_YELLOW,
                        UI.ANSI_RESET,
                        UI.ANSI_RESET,
                        UI.ANSI_RESET,
                        UI.ANSI_WHITE,
                        UI.ANSI_RESET);

        ui.printBoard(pieces, null);
        assertEquals(stringExpected, outputStreamCaptor.toString());
    }

    @Test
    @DisplayName("should print the board, pieces, its corresponding colors and possibleMoves")
    void printBoard_hasPossibleMoves_printBoardPiecesColorsMoves() {
        Board board = new Board(4, 1);

        ChessPiece[][] pieces =
                new ChessPiece[][] {
                    {new Rook(board, Color.BLACK)}, {null}, {null}, {new Rook(board, Color.WHITE)}
                };

        boolean[][] possibleMoves = new boolean[][] {{true}, {true}, {false}, {false}};

        String stringExpected =
                String.format(
                        "8 %s%sR%s %n7 %s-%s %n6 -%s %n5 %sR%s %n  a b c d e f g h%n",
                        UI.ANSI_BLUE_BACKGROUND,
                        UI.ANSI_YELLOW,
                        UI.ANSI_RESET,
                        UI.ANSI_BLUE_BACKGROUND,
                        UI.ANSI_RESET,
                        UI.ANSI_RESET,
                        UI.ANSI_WHITE,
                        UI.ANSI_RESET);

        ui.printBoard(pieces, possibleMoves);
        assertEquals(stringExpected, outputStreamCaptor.toString());
    }
}
