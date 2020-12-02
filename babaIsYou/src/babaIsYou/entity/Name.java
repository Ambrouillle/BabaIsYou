package babaIsYou.entity;

public class Name extends Text {
	private int objectId;

	protected Name(int imageID ,int objectID) {
		super(imageID);
		this.objectId = objectID;
	}
	

}
