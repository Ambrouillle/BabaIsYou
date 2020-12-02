package babaIsYou.entity;

public class Property extends Text {
	private int objectId;
	
	public Property(int imageID,int objectID) {
		super(imageID);
		this.objectId = objectID;
	}

}
