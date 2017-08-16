package com.shatranjava.engine.pieces;

import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.board.Move;

import java.util.List;

/**
 * Created by Ankur Gupta on 15/8/17.
 * guptaankur.gupta0@gmail.com
 * <p>
 * This class represents a Piece out of 16 in chess
 */

public abstract class Piece {

    private final int mPiecePosition;
    private final Alliance mPieceAlliance;

    Piece(final int piecePosition, final Alliance pieceAlliance) {
        mPieceAlliance = pieceAlliance;
        mPiecePosition = piecePosition;
    }

    public abstract List<Move> calculateLegalMoves(final Board board);
}
