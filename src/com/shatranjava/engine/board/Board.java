package com.shatranjava.engine.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.pieces.Piece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ankur Gupta on 15/8/17.
 * guptaankur.gupta0@gmail.com
 */
public class Board {
    private static final int TILES_PER_ROW = 8;
    private final Map<Coordinate, Tile> mGameBoard;

    public Board(Builder builder) {
        this.mGameBoard = createGameBoard(builder);
    }

    public Tile getTile(final Coordinate tileCoordinate) {
        return null;
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
