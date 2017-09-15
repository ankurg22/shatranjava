package com.shatranjava.gui;

import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.board.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Table {
    private final JFrame mGameFrame;
    private final BoardPanel mBoardPanel;
    private final Board mChessBoard;

    private static final Dimension FRAME_DIMENSION_OUTER = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String pieceImagePath = "art/pieces/";

    private Color lightColor = new Color(225, 225, 225);
    private Color darkColor = new Color(161, 183, 243);

    public Table() {
        mGameFrame = new JFrame("ShatranJava");
        mGameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        mGameFrame.setJMenuBar(tableMenuBar);
        mGameFrame.setSize(FRAME_DIMENSION_OUTER);

        mChessBoard = Board.createStandardBoard();

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

        final JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(openPGN);
        fileMenu.add(exitMenu);
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
            assignTilePieceIcon(mChessBoard);
            validate();
        }

        private void assignTileColor() {
            if (tileCoordinate.getX() % 2 == 0) {
                setBackground(tileCoordinate.getY() % 2 == 0 ? lightColor : darkColor);
            } else if (tileCoordinate.getX() % 2 != 0) {
                setBackground(tileCoordinate.getY() % 2 == 0 ? darkColor : lightColor);
            }
        }

        private void assignTilePieceIcon(Board board) {
            removeAll();
            if (board.getTile(tileCoordinate).isTileOccupied()) {
                try {
                    String path = pieceImagePath + board.getTile(tileCoordinate).getPiece().getPieceAlliance().toString().substring(0, 1) +
                            board.getTile(tileCoordinate).getPiece().getPieceType().toString() + ".gif";
                    BufferedImage icon =
                            ImageIO.read(new File(path));
                    add(new JLabel(new ImageIcon(icon)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
