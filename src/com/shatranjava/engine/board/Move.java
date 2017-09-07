package com.shatranjava.engine.board;

import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.pieces.Piece;
import com.shatranjava.engine.player.Player;

/**
 * Created by Ankur Gupta on 15/8/17.
 * guptaankur.gupta0@gmail.com
 * <p>
 * This defines the type of move which piece has to take.
 */
public abstract class Move {

    protected final Board mBoard;
    protected final Piece mPieceMoved;
    private final Coordinate mDestinationCoordinate;

    private Move(final Board board,
                 final Piece pieceMoved,
                 final Coordinate destinationCoordinate) {
        mBoard = board;
        mPieceMoved = pieceMoved;
        mDestinationCoordinate = destinationCoordinate;
    }

    public Coordinate getDestinationCoordinate() {
        return mDestinationCoordinate;
    }

    public Board execute() {
        final Board.Builder builder = new Board.Builder();
        for (Piece piece : mBoard.getCurrentPlayer().getActivePieces()) {
            if (!mPieceMoved.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (Piece piece : mBoard.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }

        builder.setPiece(mPieceMoved.movePiece(this));
        builder.setMoveMaker(mBoard.getCurrentPlayer().getOpponent().getAlliance());
        return null;
    }

    public Piece getPieceMoved() {
        return mPieceMoved;
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece pieceMoved,
                         final Coordinate destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

    }

    public static final class AttackMove extends Move {

        private final Piece mPieceAttacked;

        public AttackMove(final Board board,
                          final Piece pieceMoved,
                          final Coordinate destinationCoordinate,
                          final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate);
            mPieceAttacked = pieceAttacked;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
