package com.shatranjava.engine.player;

import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.board.Move;
import com.shatranjava.engine.pieces.King;
import com.shatranjava.engine.pieces.Piece;

import java.util.Collection;

public abstract class Player {

    protected final Board mBoard;
    protected final King mPlayerKing;
    protected final Collection<Move> mLegalMoves;

    public Player(final Board board,
                  final Collection<Move> legalMoves,
                  final Collection<Move> opponentMoves) {
        mBoard = board;
        mPlayerKing = establishKing();
        mLegalMoves = legalMoves;
    }

    private King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("You can't have an Board without a King.");
    }

    public boolean isMoveLegal(final Move move) {
        return mLegalMoves.contains(move);
    }

    public boolean isInCheck() {
        return false;
    }

    public boolean isInCheckMate() {
        return false;
    }

    public boolean isInStaleMate() {
        return false;
    }

    public boolean isCastled() {
        return false;
    }

    public MoveTransition makeMove(final Move move) {
        return null;
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();
}
