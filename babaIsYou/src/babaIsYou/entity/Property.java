package babaIsYou.entity;

import java.util.List;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.PropertyEnum;

public class Property extends Text {
	private final int objectId;
	private final PropertyEnum propertyEnum;
	
	public Property(int imageID,int id,int objectID,Level level, PropertyEnum propertyEnum) {
		super(imageID, id,level);
		this.objectId = objectID;
		this.propertyEnum = propertyEnum;
	}
	
	
	@Override
	public boolean isProperty() {
		return true;
	}

	public PropertyEnum getPropertyEnum() {
		return this.propertyEnum;
	}


}
