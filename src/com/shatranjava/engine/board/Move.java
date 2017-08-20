package com.shatranjava.engine.board;

import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.pieces.Piece;

/**
 * Created by Ankur Gupta on 15/8/17.
 * guptaankur.gupta0@gmail.com
 * <p>
 * This defines the type of move which piece has to take.
 */
public abstract class Move {

    private final Board mBoard;
    private final Piece mPieceMoved;
    private final Coordinate mDestinationCoordinate;

    private Move(final Board board,
                 final Piece pieceMoved,
                 final Coordinate destinationCoordinate) {
        mBoard = board;
        mPieceMoved = pieceMoved;
        mDestinationCoordinate = destinationCoordinate;
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
    }
}
