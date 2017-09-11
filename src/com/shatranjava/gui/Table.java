package com.shatranjava.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Table {

    private final JFrame mGameFrame;

    private static final Dimension FRAME_DIMENSION_OUTER = new Dimension(600, 600);

    public Table() {
        mGameFrame = new JFrame("ShatranJava");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        mGameFrame.setJMenuBar(tableMenuBar);
        mGameFrame.setSize(FRAME_DIMENSION_OUTER);
        mGameFrame.setVisible(true);
    }

    private void populateMenuBar(JMenuBar tableMenuBar) {
        tableMenuBar.add(createFileMenu());
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
