package com.shatranjava.engine.pieces;

import com.google.common.collect.ImmutableList;
import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.board.Move;
import com.shatranjava.engine.board.Move.AttackMove;
import com.shatranjava.engine.board.Move.MajorMove;
import com.shatranjava.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    private static final Coordinate[] CANDIDATE_MOVE_COORDINATES = {
            new Coordinate(1, 0),
            new Coordinate(2, 0),
            new Coordinate(1, 1),
            new Coordinate(1, -1)
    };

    Pawn(Coordinate pieceCoordinate, Alliance pieceAlliance) {
        super(pieceCoordinate, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        Coordinate candidateDestinationCoordinate;
        List<Move> legalMoves = new ArrayList<>();

        for (final Coordinate currentCoordinate : CANDIDATE_MOVE_COORDINATES) {
            Coordinate directedCoordinate = Coordinate.mult(
                    currentCoordinate,
                    getPieceAlliance().getDirection()
            );
            candidateDestinationCoordinate = Coordinate.add(
                    getPieceCoordinate(),
                    directedCoordinate
            );

            if (candidateDestinationCoordinate.isValidCoordinate()) {

                final Tile candidateDestinationTile =
                        board.getTile(candidateDestinationCoordinate);
                //Non attacking move(forward)
                if ((currentCoordinate.getCoordinateX() == 1 ||
                        currentCoordinate.getCoordinateX() == -1) &&
                        currentCoordinate.getCoordinateY() == 0) {
                    //Forward by 1, no attacking
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board,
                                this,
                                candidateDestinationCoordinate)
                        );
                    }
                    //Non attacking move(jump)
                } else if ((currentCoordinate.getCoordinateX() == 2 ||
                        currentCoordinate.getCoordinateX() == -2 &&
                                isFirstMove())) {
                    final Coordinate behindCandidateDestinationCoordinate =
                            Coordinate.add(
                                    getPieceCoordinate(),
                                    Coordinate.mult(
                                            new Coordinate(-1, 0),
                                            getPieceAlliance().getDirection()
                                    )
                            );
                    if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                            !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                        legalMoves.add(new MajorMove(board,
                                this,
                                candidateDestinationCoordinate)
                        );
                    }
                } else if (currentCoordinate.getCoordinateX() == currentCoordinate.getCoordinateY()) {
                    if (candidateDestinationTile.isTileOccupied()) {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (pieceAlliance != getPieceAlliance()) {
                            legalMoves.add(new AttackMove(
                                    board,
                                    this,
                                    candidateDestinationCoordinate,
                                    pieceAtDestination)
                            );
                        }
                    }
                } else {
                    if (candidateDestinationTile.isTileOccupied()) {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (pieceAlliance != getPieceAlliance()) {
                            legalMoves.add(new AttackMove(
                                    board,
                                    this,
                                    candidateDestinationCoordinate,
                                    pieceAtDestination)
                            );
                        }
                    }
                }

            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
