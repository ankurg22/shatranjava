package com.shatranjava.engine;

public class Coordinate {
    private final int mCoordinateX;
    private final int mCoordinateY;

    public Coordinate(final int coordinateX, final int coordinateY) {
        mCoordinateX = coordinateX;
        mCoordinateY = coordinateY;
    }

    public static Coordinate add(final Coordinate coordinateA,
                                 final Coordinate coordinateB) {
        return new Coordinate(
                coordinateA.getCoordinateX() + coordinateB.getCoordinateX(),
                coordinateA.getCoordinateY() + coordinateB.getCoordinateY()
        );
    }

    public int getCoordinateX() {
        return mCoordinateX;
    }

    public int getCoordinateY() {
        return mCoordinateY;
    }
}
