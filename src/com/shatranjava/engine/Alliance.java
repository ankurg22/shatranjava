package com.shatranjava.engine;

import com.shatranjava.engine.player.BlackPlayer;
import com.shatranjava.engine.player.Player;
import com.shatranjava.engine.player.WhitePlayer;

/**
 * Created by Ankur Gupta on 15/8/17.
 * guptaankur.gupta0@gmail.com
 * <p>
 * enum to define possible color and direction of
 * {@link com.shatranjava.engine.pieces.Piece}
 */

public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public boolean isPawnPromotionSquare(Coordinate coordinate) {
            return coordinate.getX() == 0;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public boolean isPawnPromotionSquare(Coordinate coordinate) {
            return coordinate.getX() == 7;
        }
    };

    public abstract int getDirection();

    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);

    public abstract boolean isPawnPromotionSquare(Coordinate coordinate);
}
