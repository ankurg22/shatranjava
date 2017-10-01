package com.shatranjava.engine.pieces;

import com.google.common.collect.ImmutableList;
import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.board.Move;
import com.shatranjava.engine.board.Move.MajorAttackMove;
import com.shatranjava.engine.board.Move.MajorMove;
import com.shatranjava.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {

    private static final Coordinate[] CANDIDATE_MOVE_COORDINATES = {
            new Coordinate(-1, -1),
            new Coordinate(-1, 0),
            new Coordinate(-1, 1),
            new Coordinate(0, -1),
            new Coordinate(0, 1),
            new Coordinate(1, -1),
            new Coordinate(1, 0),
            new Coordinate(1, 1)
    };

    public King(Coordinate pieceCoordinate, Alliance pieceAlliance) {
        super(pieceCoordinate, pieceAlliance, true);
    }

    public King(Coordinate pieceCoordinate, Alliance pieceAlliance, boolean isFirstMove) {
        super(pieceCoordinate, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        Coordinate candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        for (final Coordinate currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            candidateDestinationCoordinate =
                    Coordinate.add(getPieceCoordinate(), currentCandidate);

            if (candidateDestinationCoordinate.isValidCoordinate()) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    //Empty destination, move possible
                    legalMoves.add(new MajorMove(board,
                            this,
                            candidateDestinationCoordinate)
                    );
                } else {
                    //Not empty
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (pieceAlliance != getPieceAlliance()) {
                        //Enemy
                        legalMoves.add(new MajorAttackMove(board,
                                this,
                                candidateDestinationCoordinate,
                                pieceAtDestination)
                        );
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public King movePiece(Move move) {
        return new King(move.getDestinationCoordinate(), move.getPieceMoved().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }
}
