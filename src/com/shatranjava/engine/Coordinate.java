package com.shatranjava.engine;

/**
 * Created by Ankur Gupta on 19/8/17.
 * guptaankur.gupta0@gmail.com
 *
 * This class stores the coordinated of a
 * {@link com.shatranjava.engine.pieces.Piece} and
 * {@link com.shatranjava.engine.board.Tile}
 */
public class Coordinate {
    private final int mCoordinateX;
    private final int mCoordinateY;

    public Coordinate(final int coordinateX, final int coordinateY) {
        mCoordinateX = coordinateX;
        mCoordinateY = coordinateY;
    }

    /**
     * When a piece is moved we need to add the Candidate move coordinate to
     * get the destination coordinate.
     * @param coordinateA of current position
     * @param coordinateB of candidate move
     * @return destination coordinate
     */
    public static Coordinate add(final Coordinate coordinateA,
                                 final Coordinate coordinateB) {
        return new Coordinate(
                coordinateA.getCoordinateX() + coordinateB.getCoordinateX(),
                coordinateA.getCoordinateY() + coordinateB.getCoordinateY()
        );
    }

    /**
     * To check whether the coordinate is valid and lies within the
     * {@link com.shatranjava.engine.board.Board}
     */
    public boolean isValidCoordinate() {
        if (mCoordinateX >= 0 && mCoordinateY >= 0 && mCoordinateX <= 7 && mCoordinateY <= 7){
            return true;
        }
        return false;
    }

    public int getCoordinateX() {
        return mCoordinateX;
    }

    public int getCoordinateY() {
        return mCoordinateY;
    }
}