package com.shatranjava.gui;

import com.shatranjava.engine.Alliance;
import com.shatranjava.engine.board.Move;
import com.shatranjava.engine.pieces.Piece;
import com.shatranjava.gui.Table.MoveLog;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ankur Gupta on 27/9/17.
 * guptaankur.gupta0@gmail.com
 */
public class KilledPiecesPanel extends JPanel {

    private final JPanel mNorthPanel;
    private final JPanel mSouthPanel;

    private static final Color PANEL_COLOR = Color.lightGray;
    private static final Dimension KILLED_PICES_DIMENSION = new Dimension(80, 80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public KilledPiecesPanel() {
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);

        mNorthPanel = new JPanel(new GridLayout(8, 2));
        mSouthPanel = new JPanel(new GridLayout(8, 2));
        mNorthPanel.setBackground(PANEL_COLOR);
        mSouthPanel.setBackground(PANEL_COLOR);
        add(mNorthPanel, BorderLayout.NORTH);
        add(mSouthPanel, BorderLayout.SOUTH);
        setPreferredSize(KILLED_PICES_DIMENSION);
    }

    public void redo(final MoveLog moveLog) {

        mNorthPanel.removeAll();
        mSouthPanel.removeAll();

        for (Move move : moveLog.getMoves()) {
            if (move.isAttack()) {
                final Piece killedPiece = move.getAttackedPiece();
                BufferedImage icon = null;
                try {
                    String path = "art/pieces/" + killedPiece.getPieceAlliance().toString().substring(0, 1) +
                            killedPiece.getPieceType().toString() + ".gif";
                    icon = ImageIO.read(new File(path));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (killedPiece.getPieceAlliance().equals(Alliance.WHITE)) {
                    mSouthPanel.add(new JLabel(new ImageIcon(icon)));
                } else {
                    mNorthPanel.add(new JLabel(new ImageIcon(icon)));
                }
            }
        }

        validate();

    }
}
