package com.shatranjava.engine;

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
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }
    };

    public abstract int getDirection();
}
