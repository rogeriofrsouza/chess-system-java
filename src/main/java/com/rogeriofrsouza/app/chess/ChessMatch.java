package com.rogeriofrsouza.app.chess;

import com.rogeriofrsouza.app.boardgame.Board;
import com.rogeriofrsouza.app.boardgame.Piece;
import com.rogeriofrsouza.app.boardgame.Position;
import com.rogeriofrsouza.app.chess.pieces.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ChessMatch {

    private int turn;
    private ChessPiece.Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    private Board board;

    private List<Piece> capturedPieces = new ArrayList<>();

    public static final List<ChessPiece.Name> possiblePromotedPieces =
            List.of(
                    ChessPiece.Name.BISHOP, ChessPiece.Name.KNIGHT,
                    ChessPiece.Name.ROOK, ChessPiece.Name.QUEEN);

    public ChessMatch() {
        board = new Board();
        turn = 1;
        currentPlayer = ChessPiece.Color.WHITE;

        initialSetup();
    }

    private void initialSetup() {
        placeBackRank(ChessPiece.Color.BLACK);
        placePawnRank(ChessPiece.Color.BLACK);
        placePawnRank(ChessPiece.Color.WHITE);
        placeBackRank(ChessPiece.Color.WHITE);
    }

    private void placeBackRank(ChessPiece.Color color) {
        int row = (color == ChessPiece.Color.WHITE) ? 1 : 8;

        board.placePiece(new Rook(board, color), new ChessPosition('a', row).toPosition());
        board.placePiece(new Knight(board, color), new ChessPosition('b', row).toPosition());
        board.placePiece(new Bishop(board, color), new ChessPosition('c', row).toPosition());
        board.placePiece(new Queen(board, color), new ChessPosition('d', row).toPosition());
        board.placePiece(new King(board, color, this), new ChessPosition('e', row).toPosition());
        board.placePiece(new Bishop(board, color), new ChessPosition('f', row).toPosition());
        board.placePiece(new Knight(board, color), new ChessPosition('g', row).toPosition());
        board.placePiece(new Rook(board, color), new ChessPosition('h', row).toPosition());
    }

    private void placePawnRank(ChessPiece.Color color) {
        int row = (color == ChessPiece.Color.WHITE) ? 2 : 7;

        for (char column = 'a'; column <= 'h'; column++) {
            board.placePiece(new Pawn(board, color, this), new ChessPosition(column, row).toPosition());
        }
    }

    public void computePossibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();

        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }

        Piece piece = board.getPieceAt(position);

        if (currentPlayer != ((ChessPiece) piece).getColor()) {
            throw new ChessException("The chosen piece is not yours");
        }

        piece.computePossibleMoves();

        if (!piece.isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    public void resetPossibleMoves() {
        Arrays.stream(getBoard().getSquares())
                .flatMap(Arrays::stream)
                .forEach(s -> s.setPossibleMove(false));
    }

    public void performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        validateTargetPosition(source, target);

        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        check = testCheck(getOpponentPlayer(currentPlayer));

        if (testCheckMate(getOpponentPlayer(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        ChessPiece movedPiece = (ChessPiece) board.getPieceAt(target);

        promoted = null;
        enPassantVulnerable = null;

        if (movedPiece instanceof Pawn) {
            // Special move: Promotion
            if (movedPiece.getColor() == ChessPiece.Color.WHITE
                    && target.getRow() == 0
                    || movedPiece.getColor() == ChessPiece.Color.BLACK
                            && target.getRow() == 7) {
                promoted = movedPiece;
            }

            // Special move: En Passant
            if (List.of(source.getRow() - 2, source.getRow() + 2).contains(target.getRow())) {
                enPassantVulnerable = movedPiece;
            }
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.getPieceAt(source).isTargetPossibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece movingPiece = (ChessPiece) board.removePiece(source);
        movingPiece.increaseMoveCount();

        Piece capturedPiece = board.removePiece(target);
        board.placePiece(movingPiece, target);

        if (capturedPiece != null) {
            capturedPieces.add(capturedPiece);
        }

        // Special move: Castling - kingside rook
        if (movingPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);

            ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // Special move: Castling - queenside rook
        if (movingPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);

            ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // Special move: En Passant
        if (movingPiece instanceof Pawn
                && source.getColumn() != target.getColumn()
                && capturedPiece == null) {
            int targetRow =
                    movingPiece.getColor() == ChessPiece.Color.WHITE
                            ? target.getRow() + 1
                            : target.getRow() - 1;

            Position pawnPosition = new Position(targetRow, target.getColumn());

            capturedPiece = board.removePiece(pawnPosition);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece movingPiece = (ChessPiece) board.removePiece(target);
        movingPiece.decreaseMoveCount();

        board.placePiece(movingPiece, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
        }

        // Special move: Castling - kingside rook
        if (movingPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);

            ChessPiece rook = (ChessPiece) board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // Special move: Castling - queenside rook
        if (movingPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);

            ChessPiece rook = (ChessPiece) board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // Special move: En Passant
        if (movingPiece instanceof Pawn
                && source.getColumn() != target.getColumn()
                && capturedPiece == enPassantVulnerable) {
            ChessPiece pawn = (ChessPiece) board.removePiece(target);

            int targetRow = movingPiece.getColor() == ChessPiece.Color.WHITE ? 3 : 4;
            Position pawnPosition = new Position(targetRow, target.getColumn());

            board.placePiece(pawn, pawnPosition);
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = getOpponentPlayer(currentPlayer);
    }

    private boolean testCheck(ChessPiece.Color color) {
        Position kingPosition = searchKing(color).getChessPosition().toPosition();
        ChessPiece.Color opponentPlayer = getOpponentPlayer(color);

        return getPiecesByColor(opponentPlayer)
                .stream()
                .anyMatch(piece -> {
                    boolean[][] possibleMoves = piece.computePossibleMoves();
                    return possibleMoves[kingPosition.getRow()][kingPosition.getColumn()];
                });
    }

    private ChessPiece searchKing(ChessPiece.Color color) {
        return getPiecesByColor(color)
                .stream()
                .filter(King.class::isInstance)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There is no " + color + " king on the board"));
    }

    private List<ChessPiece> getPiecesByColor(ChessPiece.Color color) {
        return board.getPiecesOnTheBoard()
                .stream()
                .map(ChessPiece.class::cast)
                .filter(p -> p.getColor() == color)
                .toList();
    }

    private ChessPiece.Color getOpponentPlayer(ChessPiece.Color color) {
        return color == ChessPiece.Color.WHITE
                ? ChessPiece.Color.BLACK
                : ChessPiece.Color.WHITE;
    }

    private boolean testCheckMate(ChessPiece.Color color) {
        if (!testCheck(color)) {
            return false;
        }

        List<ChessPiece> piecesFiltered = getPiecesByColor(color);

        for (ChessPiece piece : piecesFiltered) {
            boolean[][] possibleMoves = piece.computePossibleMoves();

            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (possibleMoves[i][j]) {
                        Position source = piece.getChessPosition().toPosition();
                        Position target = new Position(i, j);

                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);

                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public ChessPiece replacePromotedPiece(ChessPiece.Name pieceName) {
        return replacePromotedPiece(pieceName.getLetter());
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalStateException("There is no piece to be promoted");
        }

        if (!List.of("B", "N", "R", "Q").contains(type)) {
            return promoted;
        }

        Position promotedPosition = promoted.getChessPosition().toPosition();
        board.removePiece(promotedPosition);

        ChessPiece newPiece = switch (type) {
            case "B" -> new Bishop(board, promoted.getColor());
            case "N" -> new Knight(board, promoted.getColor());
            case "R" -> new Rook(board, promoted.getColor());
            default -> new Queen(board, promoted.getColor());
        };

        board.placePiece(newPiece, promotedPosition);

        return newPiece;
    }
}
