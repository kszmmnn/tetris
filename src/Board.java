import java.awt.Color;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

class Board extends JComponent implements Constants {

	private Piece activePiece;
	private Block[][] boardBlocks;

	public Board(int COLS, int ROWS) {
		super();
		setPreferredSize(new Dimension(COLS * SCALE, ROWS * SCALE));
		setMaximumSize(new Dimension(COLS * SCALE, ROWS * SCALE));
		setMinimumSize(new Dimension(COLS * SCALE, ROWS * SCALE));
		activePiece = new Piece();
		boardBlocks = new Block[COLS][ROWS];
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		activePiece.draw(g, SCALE);
		
		for (Block[] blockCol : boardBlocks) {
			for (Block b : blockCol) {
				if (b != null) {
					b.draw(g, SCALE);
				}
			}
		}
	}

	public void nextTurn() {
		if (activePiece.checkCollision(boardBlocks)) {
			activePiece.moveToTab(boardBlocks);
			if (deleteFullLines()) {

				moveLinesDown();
			}
			activePiece = new Piece();
		} else {
			activePiece.move(0, 1);
		}
		this.repaint();
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

	private Boolean deleteFullLines() {
		Boolean ret = false;
		for (int i = 0; i < ROWS; ++i) {
			if (isLineFull(i)) {
				deleteLine(i);
				ret = true;
			}
		}
		return ret;
	}

	private Boolean isLineNull(int line) {
		for (int i = 0; i < COLS; ++i) {
			if (boardBlocks[i][line] != null)
				return false;
		}
		return true;
	}

	private Boolean isLineFull(int line) {
		for (int i = 0; i < COLS; ++i) {
			if (boardBlocks[i][line] == null)
				return false;
		}
		return true;
	}

	private void deleteLine(int line) {
		for (int i = 0; i < COLS; ++i) {
			boardBlocks[i][line] = null;
		}
	}

	private void moveLinesDown() {
		for(int i=0; i<ROWS-1; ++i) {
			if(isLineNull(i+1) && !isLineNull(i)) {
				moveLine(i);
				i=0;
			}
		}
	}

	private void moveLine(int line) {
		for (int i = 0; i < COLS; ++i) {
			if (boardBlocks[i][line] != null) {
				boardBlocks[i][line].loc.move(0, 1);
				boardBlocks[i][line + 1] = boardBlocks[i][line];
				boardBlocks[i][line] = null;

			}
		}
	}

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
