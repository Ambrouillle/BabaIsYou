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
	 * getter ElemId
	 * @return
	 */
	public int getElemID() {
        return this.idElement;
    }
	
	/**
	 * check if this is linked to the Property PUSH in level
	 * return true if is linked
	 * 		  false if not
	 */
	public boolean isPush() {
		if( this.getLevel().getPropertyHashMap().containsKey(this.idElement)) {
			return this.getLevel().getPropertyHashMap().get(this.idElement).contains(PropertyEnum.Push);
		}
		return false;
	}
	/**
	 * check if this is linked to the Property STOP in level
	 * return true if is linked
	 * 		  false if not
	 */
	public boolean isStop() {
		if( getLevel().getPropertyHashMap().containsKey(this.idElement)) {
			return getLevel().getPropertyHashMap().get(this.idElement).contains(PropertyEnum.Stop);
		}
		return false; 
	}

	@Override
	/**
	 * function that return the List of all properties linked to this in Level
	 * return the List of all properties linked to this in Level
	 */
	protected List<PropertyEnum> getProperties() {		
		return this.getLevel().getPropertyHashMap().get(this.idElement);
	}
	@Override
	public boolean isYou() {
		return this.getLevel().getPropertyHashMap().get(this.idElement).contains(PropertyEnum.You);
	}
}