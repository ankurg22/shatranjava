package com.shatranjava.engine.player;

import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.board.Move;
import com.shatranjava.engine.pieces.King;
import com.shatranjava.engine.pieces.Piece;

import java.util.Collection;

public abstract class Player {

    protected final Board mBoard;
    protected final King mPlayerKing;
    protected final Collection<Move> mLegalMoves;

    Player(final Board board,
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

    protected abstract Collection<Piece> getActivePieces();
}
