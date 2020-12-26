package babaIsYou.entity;

import java.util.List;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.PropertyEnum;

public class Property extends Text {
	private final int objectId;
	
	public Property(int imageID,int id,int objectID,Level level) {
		super(imageID, id,level);
		this.objectId = objectID;
	}



}
