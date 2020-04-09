import java.awt.Color;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

class Board extends JComponent {

	private static final int SCALE = 30; // pixels per square

	private int cols;
	private int rows;
	private Piece activePiece;
	private Block[][] boardBlocks;

	public Board(int cols, int rows) {
		super();
		setPreferredSize(new Dimension(cols * SCALE - 9, rows * SCALE - 9));
		activePiece = new Piece();
		boardBlocks = new Block[cols][rows];
	}

	public void paintComponent(Graphics g) {
		// clear the screen with black
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		activePiece.draw(g, SCALE);

		for (Block[] blockColl : boardBlocks) {
			for (Block block : blockColl) {
				if (block != null) {
					block.draw(g, SCALE);
				}
			}
		}
		// showTab();

	}

	public void nextTurn() {
		showTab();
		if (activePiece.checkCollision(boardBlocks)) {// true if collision
			activePiece.moveToTab(boardBlocks);
			if (activePiece.deleteFullLines(boardBlocks)) {
				moveLinesDown();
			}
			activePiece = new Piece();
		} else {
			activePiece.move(0, 1);
		}

		repaint();
	}

	public void slide(int dx) {
		if (dx == 1) {
			if (activePiece.isMoveRightAvailable(boardBlocks))
				activePiece.move(dx, 0);
		} else if (dx == -1) {
			if (activePiece.isMoveLeftAvailable(boardBlocks))
				activePiece.move(dx, 0);
		} else {
			Error er = new Error("invalid slide");
			throw (er);
		}
		repaint();
	}

	public void moveLinesDown() {
		for (int i = boardBlocks[0].length-2; i != 1; --i) {
			if (activePiece.isLineNull(boardBlocks, i) && activePiece.isLineNull(boardBlocks, i+1))
				for (int j = 0; j < boardBlocks.length; ++j) {
					if (boardBlocks[j][i] != null) {
						boardBlocks[j][i].loc.y s	;
					}
					System.out.println("dupa");
				}
		}
		repaint();
	}

//	public void moveLinesDown() {
//		for(int i=0; i<boardBlocks[0].length; ++i) {
//				if(activePiece.isLineNull(boardBlocks, i))
//					for(int j=0; j<boardBlocks.length; ++j) {
//						if(boardBlocks[j][i]!=null) {
//							boardBlocks[j][i].loc.y++;
//						}
//						System.out.println("dupa");				
//			}
//		}
//		repaint();
//	}

	public void rotateRight() {
		activePiece = activePiece.rotateR(boardBlocks);
		repaint();
	}

	public void rotateLeft() {
		activePiece = activePiece.rotateL(boardBlocks);
		repaint();
	}

	public void showTab() {
		for (Block[] col : boardBlocks) {
			for (Block b : col) {
				if (b != null) {
					System.out.print("[1]");
				} else {
					System.out.print("[0]");
				}
			}
			System.out.println();

		}
		System.out.println();
		System.out.println();
	}

}
