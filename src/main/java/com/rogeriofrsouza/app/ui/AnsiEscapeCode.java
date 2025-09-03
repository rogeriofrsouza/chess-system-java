package com.rogeriofrsouza.app.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AnsiEscapeCode {

    RESET("\u001B[0m"),
    YELLOW("\u001B[33m"),
    WHITE("\u001B[37m"),
    BLUE_BACKGROUND("\u001B[44m"),
    MOVE_CURSOR_HOME("\033[H"),
    CLEAR_SCREEN("\033[2J");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
