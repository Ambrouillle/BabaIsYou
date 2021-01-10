package babaIsYou.entity;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.PropertyEnum;

public class Property extends Text {
	private final PropertyEnum propertyEnum;
	
	public Property(int imageID, int id, Level level, PropertyEnum propertyEnum) {
		super(imageID, id,level);
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
