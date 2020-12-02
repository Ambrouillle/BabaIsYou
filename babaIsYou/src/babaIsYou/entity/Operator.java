package babaIsYou.entity;

public abstract class Operator extends Text {
	private int objectId;
	
	public Operator(int imageID ,int objectID) {
		super(imageID);
		this.objectId = objectID;
	}
}
