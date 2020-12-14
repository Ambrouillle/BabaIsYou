package babaIsYou.entity;

import java.util.List;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.PropertyEnum;

public class Element extends Entity {
	/*en plus du x,y et de l'id image . un Element contient le level(map) et un id elem( savoir si c'est un mur ou un rock)
	 * creer a partir de l'enum*/
	private int idElement;
	
	public Element(int imageID,int id, int idElement,Level level) {
		super(imageID, id,level);
		this.idElement = idElement;
		
	}
		
	public int getElemID() {
        return this.idElement;
    }
	
	/*Pour voir si Element est pushable, il faut verifier si dans la map il y a la propzerty push associé a Elemnt.idElement*/
	@Override
	public boolean isPush() {
		if( this.getLevel().getPropertyHashMap().containsKey(this.idElement)) {
			return this.getLevel().getPropertyHashMap().get(this.idElement).contains(PropertyEnum.Push);
		}
		return false;
	}
	
	public boolean isStop() {
		if( getLevel().getPropertyHashMap().containsKey(this.idElement)) {
			return getLevel().getPropertyHashMap().get(this.idElement).contains(PropertyEnum.Stop);
		}
		return false; 
	}

	@Override
	protected List<PropertyEnum> getPropertys() {
		
		return this.getLevel().getPropertyHashMap().get(this.idElement);
	}
}
