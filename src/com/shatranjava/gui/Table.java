package com.shatranjava.gui;

import com.shatranjava.engine.Coordinate;

import javax.swing.*;
import java.awt.*;

public class Table {
    private final JFrame mGameFrame;
    private final BoardPanel mBoardPanel;

    private static final Dimension FRAME_DIMENSION_OUTER = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private Color lightColor = new Color(255, 255, 255);
    private Color darkColor = new Color(0, 0, 0);

    public Table() {
        mGameFrame = new JFrame("ShatranJava");
        mGameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        mGameFrame.setJMenuBar(tableMenuBar);
        mGameFrame.setSize(FRAME_DIMENSION_OUTER);

        mBoardPanel = new BoardPanel();
        mGameFrame.add(mBoardPanel, BorderLayout.CENTER);
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

    private class BoardPanel extends JPanel {
        final TilePanel[][] boardTiles;

        public BoardPanel() {
            super(new GridLayout(8, 8));
            boardTiles = new TilePanel[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    final TilePanel tilePanel = new TilePanel(this, new Coordinate(i, j));
                    boardTiles[i][j] = tilePanel;
                    add(tilePanel);
                }
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class TilePanel extends JPanel {

        private final Coordinate tileCoordinate;

        public TilePanel(final BoardPanel boardPanel,
                         final Coordinate tileCoordinate) {
            super(new GridBagLayout());
            this.tileCoordinate = tileCoordinate;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            validate();
        }

        private void assignTileColor() {
            if (tileCoordinate.getX() % 2 == 0) {
                setBackground(tileCoordinate.getY() % 2 == 0 ? lightColor : darkColor);
            } else if (tileCoordinate.getX() % 2 != 0) {
                setBackground(tileCoordinate.getY() % 2 == 0 ? darkColor : lightColor);
            }
        }


    }
}
