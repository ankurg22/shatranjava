package com.shatranjava.engine.player;

import com.google.common.collect.ImmutableList;
import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.board.Move;
import com.shatranjava.engine.pieces.King;
import com.shatranjava.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {

    protected final Board mBoard;
    protected final King mPlayerKing;
    protected final Collection<Move> mLegalMoves;
    private final boolean mIsInCheck;

    public Player(final Board board,
                  final Collection<Move> legalMoves,
                  final Collection<Move> opponentMoves) {
        mBoard = board;
        mPlayerKing = establishKing();
        mLegalMoves = legalMoves;
        mIsInCheck = !calculateAttacksOnTile(mPlayerKing.getPieceCoordinate(),
                opponentMoves).isEmpty();
    }

    private static Collection<Move> calculateAttacksOnTile(Coordinate coordinate,
                                                           Collection<Move> moves) {
        List<Move> attackMoves = new ArrayList<>();
        for (Move move : moves) {
            if (coordinate.equals(move.getDestinationCoordinate())) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
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
        return mIsInCheck;
    }

    public boolean isInCheckMate() {
        return mIsInCheck && !hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !mIsInCheck && !hasEscapeMoves();
    }

    public boolean isCastled() {
        return false;
    }

    protected boolean hasEscapeMoves() {
        for (final Move move : mLegalMoves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(mBoard, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.getCurrentPlayer()
                        .getOpponent().getPlayerKing().getPieceCoordinate(),
                transitionBoard.getCurrentPlayer().getLegalMoves());

        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(mBoard, move, MoveStatus.CHECK_MATE);
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();

    public King getPlayerKing() {
        return mPlayerKing;
    }

    public Collection<Move> getLegalMoves() {
        return mLegalMoves;
    }
}
