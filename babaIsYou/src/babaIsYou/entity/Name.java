package babaIsYou.entity;

import java.security.CryptoPrimitive;
import java.util.ArrayList;
import java.util.HashMap;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.PropertyEnum;

public class Name extends Text {
	private final int objectId;
	private int linkId;
	private Entity[][] memory;
	
	@Override
	public String toString() {
		return super.toString() + " " + objectId;
	}

	protected Name(int imageID,int id,int objectID,int linkId,Level level) {
		super(imageID, id,level);
		this.objectId = objectID;
		this.linkId = linkId;
		memory = new Entity[2][2];
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param text
	 * @param enter
	 */
	public void notifyMe(int x, int y, Entity text, boolean enter) {
		System.out.println("x , y = "+x+" "+y + " " + text + " "+enter);	
		int placement;
		Entity[] lign;
		if(this.getx()==x) {
			lign = memory[0];
			placement = y - this.gety() -1; 
		}
		else {
			lign = memory[1];
			placement = x - this.getx() -1; 
		}
		
		if(enter) {
			lign[placement]= text;
			if(lign[0] != null && lign[1] != null && lign[0].isOperator() && lign[1].isProperty()) {
				this.getLevel().addPropInMap(((Property)lign[1]).getPropertyEnum() , this.linkId);//ajout prop
				System.out.println("ajout de prop " + this.linkId);
			}
		}
		else {

			if(lign[0] != null && lign[1] != null && lign[0].isOperator() && lign[1].isProperty() && lign[placement] == text) {
				
				
				this.getLevel().removePropInMap(((Property)lign[1]).getPropertyEnum(), this.linkId);				;//retirer prop
				System.out.println("supp de prop " + this.linkId);
			}
			lign[placement]= null;

		}
		
		
		
//		
//		if(enter) {
//			System.out.println("if");
//			for(Entity entity : this.getLevel().getPlateau()[this.getx()+1][this.gety()].getContent() ) {
//				System.out.println("elem : "+ entity);
//				if(entity.isOperator()) {
//						for(Entity entity2 : this.getLevel().getPlateau()[this.getx()+2][this.gety()].getContent()) {
//							if(entity.isProperty()) {
//									this.getLevel().addPropInMap(((Property)entity2).getPropertyEnum() , this.objectId);
//									//add the new property in the Level
//									System.out.println("notify me "+((Property)entity2).getPropertyEnum() + this.objectId);
//								}
//								
//						
//					}
//				}
//			}
//			System.out.println("fin prem for");
//		}
//			
//		else {
//			System.out.println("else");
//			/*
//			for(Entity entity : this.getLevel().getPlateau()[this.getx()][this.gety()+1].getContent() ) {
//			if(entity.isText()) {
//				if(entity.isOperator()) {
//					if(text.isProperty()) {
//						this.getLevel().removePropInMap((Property)text, this.objectId);						
//						//remove the prop from Level
//						
//					}
//				}
//			}
//		}*/
//		}
	}
	
	@Override
	public void exiting(int x, int y) {
		notifyMe(x+1, y, memory[0][0], false);
		notifyMe(x, y+1, memory[1][0], false);
		memory = new Entity[2][2];
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
	@Override
	public boolean isName() {
		return true;
	}

	
	

}