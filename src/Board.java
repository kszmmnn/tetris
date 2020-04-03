import java.awt.Color;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

class Board extends JComponent {

	private static final int SCALE = 30; // number of pixels per square

	private int cols;
	private int rows;
	private Piece activePiece;
	private Block[][] boardBlocks;

	public Board(int cols, int rows) {
		super();
		setPreferredSize(new Dimension(cols * SCALE-9, rows * SCALE-9));
		activePiece = new Piece();
		boardBlocks = new Block[cols][rows];
	}

	public void paintComponent(Graphics g) {
		// clear the screen with black
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		activePiece.draw(g, SCALE);
		
		for(Block [] blockColl : boardBlocks) {
			for(Block block : blockColl) {
				if(block != null) {
					block.draw(g, SCALE);
				}
			}
		}
	//	showTab();
		
	}

	// Move the active piece down one step. Check for collisions.
	//  Check for complete rows that can be destroyed.
	public void nextTurn() {
		
		if(activePiece.checkCollision(boardBlocks)) {//true if collision
			activePiece.moveToTab(boardBlocks);
			activePiece.deleteFullLines(boardBlocks);
			activePiece = new Piece();
		//	activePiece.deleteFullLines(boardBlocks);
		}
		else {
			activePiece.move(0,1);
		}
		
		repaint();
	}

	public void slide(int dx) {
		// TO DO: move the active piece one square in the direction dx
		// (which has a value -1 or 1):
		if(dx == 1 ) {
			if(activePiece.isMoveRightAvailable(boardBlocks))
				activePiece.move(dx,0);
		}
		else if(dx == -1) {
			if(activePiece.isMoveLeftAvailable(boardBlocks))
				activePiece.move(dx,0);
		}
		else
		{
			Error er = new Error("invalid slide");
			throw(er);
		}
		
		repaint();
	}

	public void rotateRight() {
		// TO DO: rotate the active piece to the right:


		repaint();
	}

	public void rotateLeft() {
		// TO DO: rotate the active piece to the left:
		repaint();
	}

	public void showTab() {
		for(Block[] col:boardBlocks) {
			for(Block b: col) {
				if(b!= null) {
					System.out.print("[1]");
				}
				else {
					System.out.print("[0]");
				}
			}
			System.out.println();
			
		}
		System.out.println();
		System.out.println();
	}

}

