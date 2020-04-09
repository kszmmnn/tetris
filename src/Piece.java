import java.util.Random;
import java.awt.Graphics;

//16 width
//32 height

public class Piece {
	public Block[] blocks;
	public int type;
	public int color;

	Piece() {
		Random rand = new Random();
		color = rand.nextInt(7);
		type = rand.nextInt(7);
		blocks = new Block[4];
		type = 0;

		switch (type) {
		case 0: // square
			blocks[0] = new Block(color, 7, 0);
			blocks[1] = new Block(color, 8, 0);
			blocks[2] = new Block(color, 7, 1);
			blocks[3] = new Block(color, 8, 1);
			break;

		case 1: // Z
			blocks[0] = new Block(color, 6, 0);
			blocks[1] = new Block(color, 7, 0);
			blocks[2] = new Block(color, 7, 1);
			blocks[3] = new Block(color, 8, 1);
			break;

		case 2: // anti-Z
			blocks[0] = new Block(color, 7, 0);
			blocks[1] = new Block(color, 8, 0);
			blocks[2] = new Block(color, 7, 1);
			blocks[3] = new Block(color, 6, 1);
			break;

		case 3: // line
			blocks[0] = new Block(color, 6, 0);
			blocks[1] = new Block(color, 8, 0);
			blocks[2] = new Block(color, 7, 0);
			blocks[3] = new Block(color, 9, 0);
			break;

		case 4: // 3-ending
			blocks[0] = new Block(color, 7, 0);
			blocks[1] = new Block(color, 8, 1);
			blocks[2] = new Block(color, 7, 1);
			blocks[3] = new Block(color, 7, 2);
			break;

		case 5:// L
			blocks[0] = new Block(color, 8, 2);
			blocks[1] = new Block(color, 7, 2);
			blocks[2] = new Block(color, 7, 1);
			blocks[3] = new Block(color, 7, 0);
			break;

		case 6:// anti - L
			blocks[0] = new Block(color, 7, 2);
			blocks[1] = new Block(color, 8, 2);
			blocks[2] = new Block(color, 8, 1);
			blocks[3] = new Block(color, 8, 0);
			break;

		default:
			Error err = new Error("piece setting error");
			throw (err);
		}
	}

	public void move(int dx, int dy) {
		for (Block b : blocks) {
			b.loc.x += dx;
			b.loc.y += dy;
		}
	}

	public void draw(Graphics g, int scale) {
		for (Block b : blocks) {
			b.draw(g, scale);
		}
	}

	public boolean isMoveRightAvailable(Block[][] tab) {
		for (Block b : blocks) {
			if (b.loc.x >= 15)
				return false;
			else if (tab[b.loc.x + 1][b.loc.y] != null)
				return false;
		}

		return true;
	}

	public boolean isMoveLeftAvailable(Block[][] tab) {
		for (Block b : blocks) {
			if (b.loc.x <= 0)
				return false;
			else if (tab[b.loc.x - 1][b.loc.y] != null)
				return false;
		}
		return true;
	}

	public boolean checkCollision(Block[][] tab) {
		for (Block b : blocks) {
			if (b.loc.y == 31) {
				return true;
			}
			if (tab[b.loc.x][b.loc.y + 1] != null) {
				if (tab[b.loc.x][b.loc.y + 1] != null) {
					return true;
				}
			}
		}
		return false;
	}

	public void moveToTab(Block[][] tab) {
		for (Block b : blocks) {
			tab[b.loc.x][b.loc.y] = b.clone();
		}
	}

	public Boolean deleteFullLines(Block[][] tab) {
		Boolean ret = false;
		for (int i = 0; i < tab[0].length; ++i) {
			if (isLineFull(tab, i)) {
				deleteLine(tab, i);
				for(int j=0; j<tab[0].length-1;++j) {
					for(int k=0; k<tab.length;++k) {
						if(tab[k][j]!=null)
							tab[k][j].loc.y++;
					}
				}
			}
		}
		return ret;
	}

	private void deleteLine(Block[][] tab, int line) {
		for (int i = 0; i < tab.length; ++i) {
			// tab[0][31] = null;
			tab[i][line] = null;
			for (int j = 0; j < tab[0].length; ++j) {
				if (tab[i][j] != null)
					System.out.print(i + " " + j + " " + tab[i][j]);
				System.out.println("");
			}
		}
	}

	private void moveLineDown(Block[][] tab, int line) {
		for (int i = 0; i < tab.length; ++i) {

			if ((tab[i][line] != null) && (tab[i][line + 1] == null))
				System.out.println("dupa");
			if (tab[i][line] != null)
				;
			tab[i][line].loc.y++;
		}
	}

	public Boolean isLineFull(Block[][] tab, int line) {
		for (int i = 0; i < tab.length; ++i) {
			if (tab[i][line] == null)
				return false;
		}
		return true;
	}

	public Boolean isLineNull(Block[][] tab, int line) {
		for (int i = 0; i < tab.length; ++i) {
			if (tab[i][line] == null)
				System.out.println(i + " " + line);
			return true;
		}
		return false;
	}

//	public Boolean isLineNull(Block[][] tab) {
//		System.out.println();
//		for (int i = 0; i < 16; ++i) {
//
//			System.out.println(tab[i][31]);
//			if (tab[i][31] != null)
//				return false;
//		}
//		return true;
//	}

	public Piece rotateR(Block[][] tab) {
		switch (this.type) {
		case 0: // square
			return this;

//		case 3://line
//			
//			break;

		default:
			Piece cpy = this.clone();
			Piece ret = this.performRotation(-1, 1);
			if (isRotationAvailable(ret, tab))
				return ret;
			return cpy;
		}
	}

	public Piece rotateL(Block[][] tab) {
		switch (this.type) {
		case 0: // square
			return this;

//		case 3://line
//			
//			break;

		default:
			Piece cpy = this.clone();
			Piece ret = this.performRotation(1, -1);
			if (isRotationAvailable(ret, tab))
				return ret;
			return cpy;
		}
	}

	private Boolean isRotationAvailable(Piece piece, Block[][] board) {
		for (Block b : piece.blocks) {
			if (b.loc.x < 0 || b.loc.x > 15)
				return false;
			if (b.loc.y < 0 || b.loc.y > 31)
				return false;
			if (board[b.loc.x][b.loc.y] != null)
				return false;
		}
		return true;
	}

	private Piece performRotation(int xRot, int yRot) {// not for line
		Piece ret = new Piece();
		ret.type = this.type;
		ret.color = this.color;
		int dx = blocks[2].loc.x;// used to move blocks to 0X0Y
		int dy = blocks[2].loc.y;
		for (int i = 0; i < 4; ++i) {
			this.blocks[i].loc.x -= dx; // move to 0X0Y
			this.blocks[i].loc.y -= dy;
			// rotate and move away from 0X0Y
			ret.blocks[i] = new Block(color, xRot * this.blocks[i].loc.y + dx, yRot * this.blocks[i].loc.x + dy);
		}
		return ret;
	}

	protected Piece clone() {
		Piece ret = new Piece();
		ret.color = this.color;
		ret.type = this.type;
		for (int i = 0; i < 4; ++i) {
			ret.blocks[i] = new Block(color, this.blocks[i].loc.x, this.blocks[i].loc.y);
		}
		return ret;
	}
}
