package com.rogeriofrsouza.app.boardgame;

import lombok.Data;

@Data
public class BoardSquare {

    private final Position position;
    private Piece piece;
    private boolean isPossibleMove;
}
