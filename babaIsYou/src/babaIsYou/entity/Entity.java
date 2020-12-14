package babaIsYou.entity;

public abstract class Entity {
	/*each entity have x and y linked to the number of the cell place in the level)*/
	/*chaque entity a un image ID ( correpondant au numero d'image qu'on va associé)*/
	private int imageID;
	private int x;
	private int y;
	private int id;
	public boolean isText() {
		return false;
	}
	
	public Entity(int imageID, int id) {
		this.imageID = imageID;
		this.id = id;
	}

	public int getid() {
		return this.id;
	}

	public int getImageID() {
		return this.imageID;
	}
	/*x getteur*/
	public int getx() {
		return this.x;
	}
	/*x setteur*/
	public void setx(int x2) {
		this.x = x2;
	}
	
	/*y getteur*/
	public int gety() {
		return this.y;
	}
	/*x setteur*/
	public void sety(int y2) {
		this.y = y2;
	}
	
	public abstract boolean isPush();
	public abstract boolean isStop();
	
	
}
