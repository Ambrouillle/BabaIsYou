package babaIsYou.entity;

public abstract class Operator extends Text {
	private int objectId;
	
	public Operator(int imageID ,int id,int objectID) {
		super(imageID, id);
		this.objectId = objectID;
	}
}
