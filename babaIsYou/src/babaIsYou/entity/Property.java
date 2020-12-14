package babaIsYou.entity;

public class Property extends Text {
	private int objectId;
	
	public Property(int imageID,int id,int objectID) {
		super(imageID, id);
		this.objectId = objectID;
	}

}
