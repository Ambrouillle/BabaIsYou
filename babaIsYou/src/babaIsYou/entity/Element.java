package babaIsYou.entity;

import babaIsYou.entity.entityEnum.PropertyEnum;


import babaIsYou.Level;

public class Element extends Entity {
	/*en plus du x,y et de l'id image . un Element contient le level(map) et un id elem( savoir si c'est un mur ou un rock)
	 * creer a partir de l'enum*/
	private int idElement;
	private Level level;
	
	public Element(int imageID,int id, int idElement,Level level) {
		super(imageID, id);
		this.idElement = idElement;
		this.level = level;
		
	}
		
	public int getElemID() {
        return this.idElement;
    }
	
	/*Pour voir si Element est pushable, il faut verifier si dans la map il y a la propzerty push associé a Elemnt.idElement*/
	@Override
	public boolean isPush() {
		if( level.propertyHashMap.containsKey(this.idElement)) {
			return level.propertyHashMap.get(this.idElement).contains(PropertyEnum.Push);
		}
		return false;
	}
	
	public boolean isStop() {
		if( level.propertyHashMap.containsKey(this.idElement)) {
			return level.propertyHashMap.get(this.idElement).contains(PropertyEnum.Stop);
		}
		return false; 
	}
}
