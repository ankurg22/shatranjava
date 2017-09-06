package com.shatranjava.engine.player;

public enum MoveStatus {
    DONE {
        @Override
        boolean isDone() {
            return true;
        }
    },
    ILLEGAL_MOVE {
        @Override
        boolean isDone() {
            return false;
        }
    },
    CHECK_MATE {
        @Override
        boolean isDone() {
            return false;
        }
    };
    abstract boolean isDone();
}
