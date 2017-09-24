package com.shatranjava.engine.pieces;

import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.board.Move;

import java.util.Collection;

/**
 * Created by Ankur Gupta on 15/8/17.
 * guptaankur.gupta0@gmail.com
 * <p>
 * This class represents a Piece out of 16 in chess
 */

public abstract class Piece {
    private final Coordinate mPieceCoordinate;
    private final Alliance mPieceAlliance;
    private boolean mIsFirstMove;
    private int mCachedHashCode;

    Piece(final Coordinate pieceCoordinate, final Alliance pieceAlliance, final boolean isFirstMove) {
        mPieceAlliance = pieceAlliance;
        mPieceCoordinate = pieceCoordinate;
        mIsFirstMove = isFirstMove;
        mCachedHashCode = computeHashCode();
    }


    private int computeHashCode() {
        int result = mPieceCoordinate.hashCode();
        result = 31 * result + mPieceAlliance.hashCode();
        result = 31 * result + (mIsFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public int hashCode() {
        return mCachedHashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) {
            return false;
        }
        Piece piece = (Piece) o;
        return mPieceCoordinate.equals(piece.getPieceCoordinate()) &&
                mPieceAlliance == piece.getPieceAlliance() &&
                mIsFirstMove == piece.isFirstMove();
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public Coordinate getPieceCoordinate() {
        return mPieceCoordinate;
    }

    public Alliance getPieceAlliance() {
        return mPieceAlliance;
    }

    public boolean isFirstMove() {
        return mIsFirstMove;
    }

    public abstract PieceType getPieceType();

    public abstract Piece movePiece(Move move);

    public enum PieceType {
        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private String mPieceName;

        PieceType(String pieceName) {
            mPieceName = pieceName;
        }

        public abstract boolean isKing();

        @Override
        public String toString() {
            return mPieceName;
        }
    }
}
