package babaIsYou.entity;

import java.util.ArrayList;
import java.util.List;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.PropertyEnum;

public abstract class Text extends Entity {
	public boolean isUsed;

	public Text(int imageID, int id,Level level) {
		super(imageID, id, level);
		isUsed = false;
	}
	@Override
	public final boolean isPush() {
		return true;
	}
	
	public final boolean isStop() { // on peut tjrs pousser un text
		return false;
	}
	
	@Override
	public boolean isText() {
		return true;
	}
	
	@Override
	protected List<PropertyEnum> getPropertys() {
		List<PropertyEnum> list = new ArrayList<>();
		list.add(PropertyEnum.Push);
		
		return list;
	}

}
