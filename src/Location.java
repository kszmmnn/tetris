
public class Location {
	private int x;
	private int y;
	
	Location(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void move(int dx, int dy) {
		setLoc(getX() + dx, getY() + dy);
	}
}
