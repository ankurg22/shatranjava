package com.shatranjava.gui;

import javax.swing.*;
import java.awt.*;

public class Table {

    private final JFrame mGameFrame;

    private static final Dimension FRAME_DIMENSION_OUTER = new Dimension(600, 600);

    public Table() {
        mGameFrame = new JFrame("ShatranJava");
        final JMenuBar tableMenuBar = createTableMenuBar();
        mGameFrame.setJMenuBar(tableMenuBar);
        mGameFrame.setSize(FRAME_DIMENSION_OUTER);
        mGameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Open PGN");
        openPGN.addActionListener(actionEvent -> {
            System.out.println("Open PGN menu item clicked");
        });

        fileMenu.add(openPGN);
        return fileMenu;
    }
}
