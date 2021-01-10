package babaIsYou.entity;

import java.security.CryptoPrimitive;
import java.util.ArrayList;
import java.util.HashMap;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.PropertyEnum;

public class Name extends Text {
	private final int objectId;
	

	protected Name(int imageID,int id,int objectID,Level level) {
		super(imageID, id,level);
		this.objectId = objectID;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param text
	 * @param enter
	 */
	public void notifyMe(int x, int y, Entity text, boolean enter) {
		
		if(enter) {
			for(Entity entity : this.getLevel().getPlateau()[this.getx()][this.gety()+1].getContent() ) {
				if(entity.isText()) {
					if(entity.isOperator()) {
						for(Entity entity2 : this.getLevel().getPlateau()[this.getx()][this.gety()+2].getContent()) {
							if(entity.isText()) {
								if(entity.isProperty()) {
									this.getLevel().addPropInMap( ((Property)entity2).getPropertyEnum() , this.objectId);
									//add the new property in the Level
								}
								}
						}
					}
				}
			}
		}
			
		else {
			for(Entity entity : this.getLevel().getPlateau()[this.getx()][this.gety()+1].getContent() ) {
			if(entity.isText()) {
				if(entity.isOperator()) {
					if(text.isProperty()) {
						this.getLevel().removePropInMap((Property)text, this.objectId);						
						//remove the prop from Level
						
					}
				}
			}
		}
		}
	}
	
	@Override
	public void exiting(int x, int y) {
		this.getLevel().unSubscribeTo(this,x,y+1);
		this.getLevel().unSubscribeTo(this,x,y+2);
		
		this.getLevel().unSubscribeTo(this,x+1,y);
		this.getLevel().unSubscribeTo(this,x+2,y);
	}

	@Override
	public void entering(int x, int y) {
		this.getLevel().subscribeTo(this,x,y+1);
		this.getLevel().subscribeTo(this,x,y+2);
		
		this.getLevel().subscribeTo(this,x+1,y);
		this.getLevel().subscribeTo(this,x+2,y);
		}
//	@Override
//	public void updateProp() {
//		for(Entity entity : this.getLevel().plateau[this.getx()][this.gety()+1].getContent() ) {
//			if(entity.isText()) {
//				if(entity.isOperator()) {
//					for(Entity entity2 : this.getLevel().plateau[this.getx()][this.gety()+2].getContent()) {
//						if(entity.isText()) {
//							if(entity.isProperty()) {
//								this.getLevel().addPropInMap((PropertyEnum.entity2, 2);
//							}
//								//add property in this.level.
//								//	HashMap<Integer,ArrayList<PropertyEnum>> propertyHashMap
//								//addPropInMap(entity2, this.getIdElem( ) 
//							}
//					}
//				}
//			}
//		}
//		//if(this.getLevel().plateau[x][y+1].getContent().isText())
//	//check if  there is a line?
//	}

}
