package com.shatranjava.engine.pieces;

import com.google.common.collect.ImmutableList;
import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.board.Move;
import com.shatranjava.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

    //Possible coordinates of knight from the point where knight is put.
    //Check image on https://en.wikipedia.org/wiki/Knight_(chess)
    private final static Coordinate[] CANDIDATE_MOVE_COORDINATES = {
            new Coordinate(-2, -1),
            new Coordinate(-2, 1),
            new Coordinate(-1, -2),
            new Coordinate(-1, 2),
            new Coordinate(1, -2),
            new Coordinate(1, 2),
            new Coordinate(2, -1),
            new Coordinate(2, 1)

    };

    Knight(Coordinate pieceCoordinate, Alliance pieceAlliance) {
        super(pieceCoordinate, pieceAlliance);
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
                    legalMoves.add(new Move());
                } else {
                    //Not empty
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (pieceAlliance != getPieceAlliance()) {
                        //Enemy TODO
                        legalMoves.add(new Move());
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
