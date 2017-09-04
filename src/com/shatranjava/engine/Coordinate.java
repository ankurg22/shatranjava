package com.shatranjava.engine;

/**
 * Created by Ankur Gupta on 19/8/17.
 * guptaankur.gupta0@gmail.com
 * <p>
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
     *
     * @param coordinateA of current position
     * @param coordinateB of candidate move
     * @return destination coordinate
     */
    public static Coordinate add(final Coordinate coordinateA,
                                 final Coordinate coordinateB) {
        return new Coordinate(
                coordinateA.getX() + coordinateB.getX(),
                coordinateA.getY() + coordinateB.getY()
        );
    }

    /**
     * Required for pawns since they can move only in one direction
     *
     * @param coordinate position of {@link com.shatranjava.engine.pieces.Pawn}
     * @param direction  depends on {@link Alliance}
     * @return new Coordinate with opposite direction
     */
    public static Coordinate mult(final Coordinate coordinate,
                                  final int direction) {
        return new Coordinate(
                coordinate.getX() * direction,
                coordinate.getY() * direction
        );
    }

    @Override
    public int hashCode() {
        return mCoordinateX * 31 + mCoordinateY;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Coordinate) {
            Coordinate co = (Coordinate) o;
            return co.getX() == mCoordinateX && co.getY() == mCoordinateY;
        } else {
            return false;
        }
    }

    /**
     * To check whether the coordinate is valid and lies within the
     * {@link com.shatranjava.engine.board.Board}
     */
    public boolean isValidCoordinate() {
        if (mCoordinateX >= 0 && mCoordinateY >= 0 && mCoordinateX <= 7 && mCoordinateY <= 7) {
            return true;
        }
        return false;
    }

    public int getX() {
        return mCoordinateX;
    }

    public int getY() {
        return mCoordinateY;
    }
}
