package com.shatranjava.engine.board;

import com.google.common.collect.ImmutableMap;
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

    protected final int mTileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    private static Tile createTile(final int tileCoordinate, final Piece piece) {
        if (piece != null) return new OccupiedTile(piece, tileCoordinate);
        else return EMPTY_TILES_CACHE.get(tileCoordinate);
    }


    private Tile(int tileCoordinate) {
        mTileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();


    public abstract Piece getPiece();

    /**
     * A tile can be empty
     */
    public static class EmptyTile extends Tile {

        private EmptyTile(final int tileCoordinate) {
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

        private OccupiedTile(Piece pieceOnTile, int tileCoordinate) {
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
