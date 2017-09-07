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

    public static final Move NULL_MOVE = new NullMove();

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
