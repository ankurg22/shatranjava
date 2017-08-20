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

    Piece(final Coordinate pieceCoordinate, final Alliance pieceAlliance) {
        mPieceAlliance = pieceAlliance;
        mPieceCoordinate = pieceCoordinate;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public Coordinate getPieceCoordinate() {
        return mPieceCoordinate;
    }

    public Alliance getPieceAlliance() {
        return mPieceAlliance;
    }
}
