package com.shatranjava.engine.board;

import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.board.Board.Builder;
import com.shatranjava.engine.pieces.Pawn;
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

    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board,
                 final Piece pieceMoved,
                 final Coordinate destinationCoordinate) {
        mBoard = board;
        mPieceMoved = pieceMoved;
        mDestinationCoordinate = destinationCoordinate;
    }

    @Override
    public int hashCode() {

        int result = 31 + mPieceMoved.hashCode();
        result = 31 * result + mDestinationCoordinate.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return move.getCurrentCoordinate() == getCurrentCoordinate() &&
                move.getDestinationCoordinate() == getDestinationCoordinate() &&
                move.getPieceMoved() == getPieceMoved();
    }

    public Coordinate getDestinationCoordinate() {
        return mDestinationCoordinate;
    }

    public Board execute() {
        final Builder builder = new Builder();
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

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
    }

    public Coordinate getCurrentCoordinate() {
        return getPieceMoved().getPieceCoordinate();
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece pieceMoved,
                         final Coordinate destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

    }

    public static class AttackMove extends Move {

        private final Piece mPieceAttacked;

        public AttackMove(final Board board,
                          final Piece pieceMoved,
                          final Coordinate destinationCoordinate,
                          final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate);
            mPieceAttacked = pieceAttacked;
        }

        @Override
        public int hashCode() {
            return mPieceAttacked.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AttackMove)) return false;
            final AttackMove move = (AttackMove) o;
            return super.equals(move) && getAttackedPiece().equals(move.getAttackedPiece());
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return mPieceAttacked;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class PawnMove extends Move {

        public PawnMove(final Board board,
                        final Piece pieceMoved,
                        final Coordinate destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(final Board board,
                              final Piece pieceMoved,
                              final Coordinate destinationCoordinate,
                              final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

    }

    public static final class PawnEnPassantAttack extends PawnAttackMove {

        public PawnEnPassantAttack(final Board board,
                                   final Piece pieceMoved,
                                   final Coordinate destinationCoordinate,
                                   final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

    }

    public static final class PawnJump extends Move {

        public PawnJump(final Board board,
                        final Piece pieceMoved,
                        final Coordinate destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for (final Piece piece : mBoard.getCurrentPlayer().getActivePieces()) {
                if (!mPieceMoved.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : mBoard.getCurrentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) mPieceMoved.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(mBoard.getCurrentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    static abstract class CastleMove extends Move {

        public CastleMove(final Board board,
                          final Piece pieceMoved,
                          final Coordinate destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

    }

    public static final class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(final Board board,
                                  final Piece pieceMoved,
                                  final Coordinate destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

    }

    public static final class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(final Board board,
                                   final Piece pieceMoved,
                                   final Coordinate destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

    }

    public static final class NullMove extends Move {

        public NullMove() {
            super(null, null, null);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Null moves cannot be executed");
        }
    }

    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("This class in not instantiable");
        }

        public static Move createMove(final Board board,
                                      final Coordinate currentCoordinate,
                                      final Coordinate destinationCoordinate) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                        move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;

        }
    }
}
