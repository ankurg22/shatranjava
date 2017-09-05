package com.shatranjava.engine.player;

import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.board.Move;

public class MoveTransition {

    private final Board mTransitionBoard;
    private final Move mMove;
    private final MoveStatus mMoveStatus;

    public MoveTransition(final Board transitionBoard,
                          final Move move,
                          final MoveStatus moveStatus){
        mTransitionBoard = transitionBoard;
        mMove = move;
        mMoveStatus = moveStatus;
    }
}
