package com.shatranjava.engine.pieces;

import com.google.common.collect.ImmutableList;
import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.board.Move;
import com.shatranjava.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.shatranjava.engine.board.Move.*;

public class Bishop extends Piece {

    private final static Coordinate[] CANDIDATE_MOVE_COORDINATES = {
            new Coordinate(-1, -1),
            new Coordinate(-1, 1),
            new Coordinate(1, -1),
            new Coordinate(1, 1)
    };

    public Bishop(Coordinate pieceCoordinate, Alliance pieceAlliance) {
        super(pieceCoordinate, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        Coordinate candidateDestinationCoordinate;
        List<Move> legalMoves = new ArrayList<>();

        for (final Coordinate currentCoordinate : CANDIDATE_MOVE_COORDINATES) {
            candidateDestinationCoordinate =
                    Coordinate.add(getPieceCoordinate(), currentCoordinate);

            //do because we want to keep addind the candidate coordinate
            //for all diagonal tiles.
            do {
                if (candidateDestinationCoordinate.isValidCoordinate()) {
                    final Tile candidateDestinationTile =
                            board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        //Empty tile, we can move
                        legalMoves.add(new MajorMove(board,
                                this,
                                candidateDestinationCoordinate)
                        );
                    } else {
                        //Not empty, see who is there.
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        if (pieceAlliance != getPieceAlliance()) {
                            //Enemy
                            legalMoves.add(new AttackMove(board,
                                    this,
                                    candidateDestinationCoordinate,
                                    pieceAtDestination)
                            );
                        }
                        break; //If a piece is in diagonal stop there.
                    }
                }
                candidateDestinationCoordinate =
                        Coordinate.add(candidateDestinationCoordinate, currentCoordinate);
            } while (candidateDestinationCoordinate.isValidCoordinate());
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public Bishop movePiece(Move move) {
        return new Bishop(move.getDestinationCoordinate(), move.getPieceMoved().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }
}
