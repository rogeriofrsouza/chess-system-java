package com.rogeriofrsouza.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.rogeriofrsouza.app.chess.ChessMatch;
import com.rogeriofrsouza.app.chess.ChessPiece;

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
}
