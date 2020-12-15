package babaIsYou.entity;


import babaIsYou.Level;

public class Property extends Text {
	private int objectId;

	
	public Property(int imageID,int id,int objectID,Level level, String propName) {
		super(imageID, id,level,propName);
		this.objectId = objectID;
	}



}
