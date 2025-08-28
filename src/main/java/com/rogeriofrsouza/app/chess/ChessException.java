package com.rogeriofrsouza.app.chess;

import java.io.Serial;

import com.rogeriofrsouza.app.boardgame.BoardException;

public class ChessException extends BoardException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ChessException(String msg) {
        super(msg);
    }
}
