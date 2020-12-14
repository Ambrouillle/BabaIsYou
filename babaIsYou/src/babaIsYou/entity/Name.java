package babaIsYou.entity;

public class Name extends Text {
	private int objectId;

	protected Name(int imageID,int id,int objectID) {
		super(imageID, id);
		this.objectId = objectID;
	}
	

}
