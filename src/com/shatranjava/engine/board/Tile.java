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

    protected final int mTileCoordinateX;
    protected final int mTileCoordinateY;

    private static final Map<Integer[], EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer[], EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer[], EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Integer[] coordinate = new Integer[]{i, j};
                emptyTileMap.put(coordinate, new EmptyTile(i, j));
            }
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    private static Tile createTile(final int tileCoordinateX,
                                   final int tileCoordinateY,
                                   final Piece piece) {
        return piece != null ?
                new OccupiedTile(tileCoordinateX, tileCoordinateY, piece) :
                EMPTY_TILES_CACHE.get(new Integer[]{tileCoordinateX, tileCoordinateY});
    }


    private Tile(int tileCoordinateX, int tileCoordinateY) {
        mTileCoordinateX = tileCoordinateX;
        mTileCoordinateY = tileCoordinateY;
    }

    public abstract boolean isTileOccupied();


    public abstract Piece getPiece();

    /**
     * A tile can be empty
     */
    public static class EmptyTile extends Tile {

        private EmptyTile(final int tileCoordinateX, final int tileCoordinateY) {
            super(tileCoordinateX, tileCoordinateY);
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

        private OccupiedTile(int tileCoordinateX, int tileCoordinateY, Piece pieceOnTile) {
            super(tileCoordinateX, tileCoordinateY);
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
