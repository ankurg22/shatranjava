package com.shatranjava;

import com.shatranjava.engine.board.Board;
import com.shatranjava.gui.Table;

public class ShatranJava {

    public static void main(String[] args) {

        Board board = Board.createStandardBoard();

        System.out.println(board);

        Table table = new Table();
    }
}
