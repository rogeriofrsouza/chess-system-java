package com.rogeriofrsouza.app.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AnsiEscapeCode {

    ANSI_RESET("\u001B[0m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_WHITE("\u001B[37m"),
    ANSI_BLUE_BACKGROUND("\u001B[44m"),
    ANSI_MOVE_CURSOR_HOME("\033[H"),
    ANSI_CLEAR_SCREEN("\033[2J");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
