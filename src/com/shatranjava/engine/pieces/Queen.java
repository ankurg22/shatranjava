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

public class Queen extends Piece {
    private final static Coordinate[] CANDIDATE_MOVE_COORDINATES = {
            new Coordinate(-1, -1),
            new Coordinate(-1, 0),
            new Coordinate(-1, 1),
            new Coordinate(0, -1),
            new Coordinate(0, 1),
            new Coordinate(1, -1),
            new Coordinate(1, 0),
            new Coordinate(1, 1)
    };

    public Queen(Coordinate pieceCoordinate, Alliance pieceAlliance) {
        super(pieceCoordinate, pieceAlliance, true);
    }

    public Queen(Coordinate pieceCoordinate, Alliance pieceAlliance, boolean isFirstMove) {
        super(pieceCoordinate, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        Coordinate candidateDestinationCoordinate;
        List<Move> legalMoves = new ArrayList<>();

        for (final Coordinate currentCoordinate : CANDIDATE_MOVE_COORDINATES) {
            candidateDestinationCoordinate =
                    Coordinate.add(getPieceCoordinate(), currentCoordinate);

            //do because we want to keep adding the candidate coordinate
            //for all vertical/horizontal/diagonal tiles.
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
                            legalMoves.add(new MajorAttackMove(board,
                                    this,
                                    candidateDestinationCoordinate,
                                    pieceAtDestination)
                            );
                        }
                        break; //If a piece is in the way stop there.
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
        return PieceType.QUEEN;
    }

    @Override
    public Queen movePiece(Move move) {
        return new Queen(move.getDestinationCoordinate(), move.getPieceMoved().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }
}
