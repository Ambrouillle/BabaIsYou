package babaIsYou.entity;

import java.util.List;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.PropertyEnum;

public class Element extends Entity {
	
	private final int idElement;

	public Element(int imageID,int id, int idElement,Level level) {
		super(imageID, id,level);
		this.idElement = idElement;
		
	}
	/**
	 * ElemId getter.
	 * @return ElemId.
	 */
	public int getElemID() {
        return this.idElement;
    }
	
	/**
	 * Check if this is linked to the Property PUSH in level.
	 * @return true if is linked.
	 * 		  false if not.
	 */
	public boolean isPush() {
		if( this.getLevel().getPropertyHashMap().containsKey(this.idElement)) {
			return this.getLevel().getPropertyHashMap().get(this.idElement).contains(PropertyEnum.Push);
		}
		return false;
	}
	/**
	 * Check if this is linked to the Property STOP in level.
	 * @return true if is linked.
	 * 		  false if not.
	 */
	public boolean isStop() {
		if( getLevel().getPropertyHashMap().containsKey(this.idElement)) {
			return getLevel().getPropertyHashMap().get(this.idElement).contains(PropertyEnum.Stop);
		}
		return false; 
	}

	@Override
	protected List<PropertyEnum> getProperties() {		
		return this.getLevel().getPropertyHashMap().get(this.idElement);
	}
	@Override
	public boolean isYou() {
		return this.getLevel().getPropertyHashMap().get(this.idElement).contains(PropertyEnum.You);
	}
}
