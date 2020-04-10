import java.awt.Color;
import java.awt.Graphics;

class Block implements Cloneable, Constants{

	public int colorIndex;
	public Location loc;

	public static Color[] colors = { Color.red, Color.blue, Color.magenta, Color.orange, Color.green, Color.cyan,
			Color.yellow };

	public Block(int colorIndex, int x, int y) {
		this.colorIndex = colorIndex;
		this.loc = new Location(x,y); 
	}

	public void draw(Graphics g, int scale) {
		g.setColor(colors[colorIndex]);
		int x1 = loc.getX() * scale + 1+BORDER;
		int x2 = x1 + scale;
		int y1 = loc.getY() * scale + 1+BORDER;
		int y2 = y1 + scale;


		g.fillRect(x1, y1, scale - 2, scale - 2);
		g.setColor(Color.white);
		g.drawLine(x1 - 1, y1 - 1, x1 - 1, y2 - 1);
		g.drawLine(x1 - 1, y1 - 1, x2 - 1, y1 - 1);
		g.drawLine(x2 - 1, y1 - 1, x2 - 1, y2 - 1);
		g.drawLine(x1 - 1, y2 - 1, x2 - 1, y2 - 1);
	}
	
	@Override
	public Block clone() {
		Block b = new Block(this.colorIndex, this.loc.getX(), this.loc.getY());
		return b;
	}
	
}
