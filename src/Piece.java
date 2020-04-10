import java.util.Random;
import java.awt.Graphics;
 

public class Piece implements Constants, Cloneable {
	private Block[] blocks;
	private int type;
	private int color;

	Piece() {
		Random rand = new Random();
		color = rand.nextInt(7);
		type = rand.nextInt(7);
		blocks = new Block[4];
		switch (type) {
		case 0: // square
			blocks[0] = new Block(color, COLS/2-1, 0);
			blocks[1] = new Block(color, COLS/2, 0);
			blocks[2] = new Block(color, COLS/2-1, 1);
			blocks[3] = new Block(color, COLS/2, 1);
			break;

		case 1: // Z
			blocks[0] = new Block(color, COLS/2-1, 0);
			blocks[1] = new Block(color, COLS/2, 0);
			blocks[2] = new Block(color, COLS/2, 1);
			blocks[3] = new Block(color, COLS/2+1, 1);
			break;

		case 2: // anti-Z
			blocks[0] = new Block(color, COLS/2+1, 0);
			blocks[1] = new Block(color, COLS/2, 0);
			blocks[2] = new Block(color, COLS/2, 1);
			blocks[3] = new Block(color, COLS/2-1, 1);
			break;

		case 3: // line
			blocks[0] = new Block(color, COLS/2-2, 0);
			blocks[1] = new Block(color, COLS/2-1, 0);
			blocks[2] = new Block(color, COLS/2, 0);
			blocks[3] = new Block(color, COLS/2+1, 0);
			break;

		case 4: // 3-ending
			blocks[0] = new Block(color, COLS/2-1, 0);
			blocks[1] = new Block(color, COLS/2, 1);
			blocks[2] = new Block(color, COLS/2-1, 1);
			blocks[3] = new Block(color, COLS/2-1, 2);
			break;

		case 5:// L
			blocks[0] = new Block(color, COLS/2, 2);
			blocks[1] = new Block(color, COLS/2-1, 2);
			blocks[2] = new Block(color, COLS/2-1, 1);
			blocks[3] = new Block(color, COLS/2-1, 0);
			break;

		case 6:// anti - L
			blocks[0] = new Block(color, COLS/2, 0);
			blocks[1] = new Block(color, COLS/2, 2);
			blocks[2] = new Block(color, COLS/2, 1);
			blocks[3] = new Block(color, COLS/2-1, 2);
			break;

		default:
			Error err = new Error("piece setting error");
			throw (err);
		}
	}

	public void move(int dx, int dy) {
		for (Block b : blocks) {
			b.loc.move(dx,dy);
		}
	}

	public void draw(Graphics g, int scale) {
		for (Block b : blocks) {
			b.draw(g, scale);
		}
	}

	public boolean isMoveRightAvailable(Block[][] tab) {
		for (Block b : blocks) {
			if (b.loc.getX() >= COLS-1)
				return false;
			else if (tab[b.loc.getX() + 1][b.loc.getY()] != null)
				return false;
		}
		return true;
	}

	public boolean isMoveLeftAvailable(Block[][] tab) {
		for (Block b : blocks) {
			if (b.loc.getX() <= 0)
				return false;
			else if (tab[b.loc.getX() - 1][b.loc.getY()] != null)
				return false;
		}
		return true;
	}

	public boolean checkCollision(Block[][] tab) {
		for (Block b : blocks) {
			if (b.loc.getY() == ROWS-1) {
				return true;
			}
			if (tab[b.loc.getX()][b.loc.getY() + 1] != null) {
					return true;
			}
		}
		return false;
	}

	public void moveToTab(Block[][] tab) {
		for (Block b : blocks) {
			tab[b.loc.getX()][b.loc.getY()] = b.clone();
		}
	}


	public Piece rotateR(Block[][] tab) {
		switch (this.type) {
		case 0: // square
			return this;

		default:
			Piece ret = this.performRotation(-1, 1);
			if (isRotationAvailable(ret, tab))
				return ret;
			return this;
		}
	}

	public Piece rotateL(Block[][] tab) {
		switch (this.type) {
		case 0: // square
			return this;

		default:
			Piece ret = this.performRotation(1, -1);
			if (isRotationAvailable(ret, tab))
				return ret;
			return this;
		}
	}

	private Boolean isRotationAvailable(Piece piece, Block[][] board) {
		for (Block b : piece.blocks) {
			if (b.loc.getX() < 0 || b.loc.getX() > COLS-1)
				return false;
			if (b.loc.getY() < 0 || b.loc.getY() > ROWS-1)
				return false;
			if (board[b.loc.getX()][b.loc.getY()] != null)
				return false;
		}
		return true;
	}

	private Piece performRotation(int xRot, int yRot) {// not for line
		Piece ret = this.clone();
		int dx = blocks[2].loc.getX();// used to move blocks to 0X0Y
		int dy = blocks[2].loc.getY();
		for (Block b : ret.blocks) {
			b.loc.move(-dx, -dy);
			// rotate and move away from 0X0Y
			b.loc.setLoc(b.loc.getY()*xRot+dx, b.loc.getX()*yRot+dy);
		}
		return ret;
	}

	@Override
	public Piece clone() {
		Piece ret = new Piece();
		ret.color = this.color;
		ret.type = this.type;
		for (int i = 0; i < 4; ++i) {
			ret.blocks[i] = this.blocks[i].clone();
		}
		return ret;
	}
}
