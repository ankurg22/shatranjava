package com.shatranjava.gui;

import com.shatranjava.engine.Coordinate;
import com.shatranjava.engine.board.Board;
import com.shatranjava.engine.board.Move;
import com.shatranjava.engine.board.Tile;
import com.shatranjava.engine.pieces.Piece;
import com.shatranjava.engine.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
    private final JFrame mGameFrame;
    private final BoardPanel mBoardPanel;
    private Board mChessBoard;

    private Tile mSourceTile;
    private Tile mDestinationTile;
    private Piece mHumanMovedPiece;

    private boolean highlightLegalMoves;

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
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Open PGN");
        openPGN.addActionListener(actionEvent -> {
            System.out.println("Open PGN menu item clicked");
        });

        final JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.addActionListener(e -> System.exit(0));

        fileMenu.add(openPGN);
        fileMenu.add(exitMenu);
        return fileMenu;
    }

    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferences");

        final JCheckBoxMenuItem highlightMoves = new JCheckBoxMenuItem("Highlight legal moves", false);
        highlightMoves.addActionListener(e ->{
            highlightLegalMoves = highlightMoves.isSelected();
        });

        preferencesMenu.add(highlightMoves);
        return preferencesMenu;

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

        public void drawBoard(final Board board) {
            removeAll();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    final TilePanel tilePanel = boardTiles[i][j];
                    tilePanel.drawTile(board);
                    add(tilePanel);
                }
                validate();
                repaint();
            }
        }
    }

    private static class MoveLog {
        private final List<Move> moves;

        public MoveLog(){
            moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
            return moves;
        }

        public void addMove(final Move move){
            moves.add(move);
        }

        public int size(){
            return moves.size();
        }

        public void clear(){
            moves.clear();
        }

        public Move removeMove(int index){
            return moves.remove(index);
        }

        public boolean removeMove(Move move){
            return moves.remove(move);
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

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (isRightMouseButton(e)) {
                        mSourceTile = null;
                        mDestinationTile = null;
                        mHumanMovedPiece = null;
                    } else if (isLeftMouseButton(e)) {
                        if (mSourceTile == null) {
                            mSourceTile = mChessBoard.getTile(tileCoordinate);
                            mHumanMovedPiece = mSourceTile.getPiece();
                            if (mHumanMovedPiece == null) {
                                mSourceTile = null;
                            }
                        } else {
                            highlightLegalMoves(mChessBoard);
                            mDestinationTile = mChessBoard.getTile(tileCoordinate);
                            final Move move = Move.MoveFactory.createMove(mChessBoard, mSourceTile.getTileCoordinate(),
                                    mDestinationTile.getTileCoordinate());
                            final MoveTransition moveTransition = mChessBoard.getCurrentPlayer().makeMove(move);
                            if (moveTransition.getMoveStatus().isDone()) {
                                mChessBoard = moveTransition.getTransitionBoard();
                            }
                            mSourceTile = null;
                            mDestinationTile = null;
                            mHumanMovedPiece = null;
                        }
                        invokeLater(() -> boardPanel.drawBoard(mChessBoard));
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
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

        private void highlightLegalMoves(final Board board) {
            if (highlightLegalMoves) {
                for (final Move move : pieceLegalMoves(board)) {
                    if (move.getDestinationCoordinate().equals(tileCoordinate)) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green.png")))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(Board board) {
            if (mHumanMovedPiece != null && mHumanMovedPiece.getPieceAlliance() == board.getCurrentPlayer().getAlliance()) {
                return mHumanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        public void drawTile(Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegalMoves(board);
            validate();
            repaint();
        }
    }
}
