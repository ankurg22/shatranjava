package com.shatranjava.engine.board;

import com.google.common.collect.ImmutableMap;
import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ankur Gupta on 15/8/17.
 * guptaankur.gupta0@gmail.com
 * <p>
 * This class represent a single tile of 64 tiles in chess
 */
public abstract class Tile {

    protected final Coordinate mTileCoordinate;

    private static final Map<Coordinate, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Coordinate, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Coordinate, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                emptyTileMap.put(new Coordinate(i, j), new EmptyTile(new Coordinate(i, j)));
            }
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final Coordinate tileCoordinate,
                                  final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) :
                EMPTY_TILES_CACHE.get(tileCoordinate);
    }


    private Tile(Coordinate tileCoordinate) {
        mTileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();


    public abstract Piece getPiece();

    /**
     * A tile can be empty
     */
    public static class EmptyTile extends Tile {

        private EmptyTile(final Coordinate tileCoordinate) {
            super(tileCoordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    /**
     * A tile can be occupied by {@link Piece}
     */
    public static class OccupiedTile extends Tile {

        private final Piece mPieceOnTile;

        private OccupiedTile(Coordinate tileCoordinate, Piece pieceOnTile) {
            super(tileCoordinate);
            mPieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return mPieceOnTile;
        }
    }

}
