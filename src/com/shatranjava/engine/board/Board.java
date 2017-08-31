package com.shatranjava.engine.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.pieces.*;

import java.util.*;

/**
 * Created by Ankur Gupta on 15/8/17.
 * guptaankur.gupta0@gmail.com
 */
public class Board {
    private static final int TILES_PER_ROW = 8;
    private final Map<Coordinate, Tile> mGameBoard;
    private final Collection<Piece> mWhitePieces;
    private final Collection<Piece> mBlackPieces;

    public Board(Builder builder) {
        this.mGameBoard = createGameBoard(builder);
        mWhitePieces = calculateActivePieces(mGameBoard, Alliance.WHITE);
        mBlackPieces = calculateActivePieces(mGameBoard, Alliance.BLACK);
    }

    private Collection<Piece> calculateActivePieces(final Map<Coordinate, Tile> gameBoard,
                                                    final Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile : gameBoard.values()) {
            if (tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }
    }

    public Tile getTile(final Coordinate tileCoordinate) {
        return mGameBoard.get(tileCoordinate);
    }

    private static Map<Coordinate, Tile> createGameBoard(Builder builder) {
        final Map<Coordinate, Tile> tiles = new HashMap<>();
        for (int i = 0; i < TILES_PER_ROW; i++) {
            for (int j = 0; j < TILES_PER_ROW; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                tiles.put(coordinate,
                        Tile.createTile(coordinate, builder.mBoardConfig.get(coordinate)));
            }

        }
        return ImmutableMap.copyOf(tiles);
    }

    public static Board createStandardBoard() {
        Builder builder = new Builder();
        builder.setPiece(new Rook(new Coordinate(0, 0), Alliance.BLACK));
        builder.setPiece(new Knight(new Coordinate(0, 1), Alliance.BLACK));
        builder.setPiece(new Bishop(new Coordinate(0, 2), Alliance.BLACK));
        builder.setPiece(new Queen(new Coordinate(0, 3), Alliance.BLACK));
        builder.setPiece(new King(new Coordinate(0, 4), Alliance.BLACK));
        builder.setPiece(new Bishop(new Coordinate(0, 5), Alliance.BLACK));
        builder.setPiece(new Knight(new Coordinate(0, 6), Alliance.BLACK));
        builder.setPiece(new Rook(new Coordinate(0, 7), Alliance.BLACK));
        builder.setPiece(new Pawn(new Coordinate(1, 0), Alliance.BLACK));
        builder.setPiece(new Pawn(new Coordinate(1, 1), Alliance.BLACK));
        builder.setPiece(new Pawn(new Coordinate(1, 2), Alliance.BLACK));
        builder.setPiece(new Pawn(new Coordinate(1, 3), Alliance.BLACK));
        builder.setPiece(new Pawn(new Coordinate(1, 4), Alliance.BLACK));
        builder.setPiece(new Pawn(new Coordinate(1, 5), Alliance.BLACK));
        builder.setPiece(new Pawn(new Coordinate(1, 6), Alliance.BLACK));
        builder.setPiece(new Pawn(new Coordinate(1, 7), Alliance.BLACK));

        builder.setPiece(new Rook(new Coordinate(7, 0), Alliance.WHITE));
        builder.setPiece(new Knight(new Coordinate(7, 1), Alliance.WHITE));
        builder.setPiece(new Bishop(new Coordinate(7, 2), Alliance.WHITE));
        builder.setPiece(new Queen(new Coordinate(7, 3), Alliance.WHITE));
        builder.setPiece(new King(new Coordinate(7, 4), Alliance.WHITE));
        builder.setPiece(new Bishop(new Coordinate(7, 5), Alliance.WHITE));
        builder.setPiece(new Knight(new Coordinate(7, 6), Alliance.WHITE));
        builder.setPiece(new Rook(new Coordinate(7, 7), Alliance.WHITE));
        builder.setPiece(new Pawn(new Coordinate(6, 0), Alliance.WHITE));
        builder.setPiece(new Pawn(new Coordinate(6, 1), Alliance.WHITE));
        builder.setPiece(new Pawn(new Coordinate(6, 2), Alliance.WHITE));
        builder.setPiece(new Pawn(new Coordinate(6, 3), Alliance.WHITE));
        builder.setPiece(new Pawn(new Coordinate(6, 4), Alliance.WHITE));
        builder.setPiece(new Pawn(new Coordinate(6, 5), Alliance.WHITE));
        builder.setPiece(new Pawn(new Coordinate(6, 6), Alliance.WHITE));
        builder.setPiece(new Pawn(new Coordinate(6, 7), Alliance.WHITE));

        builder.setMoveMaker(Alliance.WHITE);
        return builder.build();
    }

    public static class Builder {

        Map<Coordinate, Piece> mBoardConfig;
        Alliance mNextMoveMaker;

        public Builder() {
        }

        public Builder setPiece(final Piece piece) {
            mBoardConfig.put(piece.getPieceCoordinate(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            mNextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
