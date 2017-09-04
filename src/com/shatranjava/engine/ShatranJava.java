package com.shatranjava.engine;

import com.shatranjava.engine.board.Board;

public class ShatranJava {

    public static void main(String[] args) {

        Board board = Board.createStandardBoard();

        System.out.println(board);
    }
}
