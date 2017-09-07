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
    protected boolean isFirstMove;

    Piece(final Coordinate pieceCoordinate, final Alliance pieceAlliance) {
        mPieceAlliance = pieceAlliance;
        mPieceCoordinate = pieceCoordinate;
        isFirstMove = false;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public Coordinate getPieceCoordinate() {
        return mPieceCoordinate;
    }

    public Alliance getPieceAlliance() {
        return mPieceAlliance;
    }

    public boolean isFirstMove() {
        return isFirstMove;
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
