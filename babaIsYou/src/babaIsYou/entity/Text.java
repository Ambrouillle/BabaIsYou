package babaIsYou.entity;

import java.util.ArrayList;
import java.util.List;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.PropertyEnum;

public abstract class Text extends Entity {

	public Text(int imageID, int id,Level level) {
		super(imageID, id, level);
		setUsed(false);
	}
	@Override
	public final boolean isPush() {
		return true;
	}
	
	public final boolean isStop() {
		return false;
	}
	
	@Override
	public boolean isText() {
		return true;
	}
	
	@Override
	protected List<PropertyEnum> getProperties() {
		List<PropertyEnum> list = new ArrayList<>();
		list.add(PropertyEnum.Push);
		
		return list;
	}
	public boolean isOperator() {
		return false;
	}
	public boolean isProperty() {
		return false;
	}

	public void setUsed(boolean isUsed) {
	}
	public boolean isYou() {
		return false;
	}
	

}
