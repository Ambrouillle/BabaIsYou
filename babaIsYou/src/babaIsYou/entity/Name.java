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
		notifyMe(x, y, text, enter, false);
	}
	
	
	private void notifyMe(int x, int y, Entity text, boolean enter, boolean forceRemove) {
		System.out.print("x , y = "+x+" "+y + " " + text + " "+enter);	
		int placement;
		Entity[] lign;
		if(this.getx()==x) {
			lign = memory[0];
			placement = y - this.gety() -1;  
			System.out.print("0");
			
		}
		else {
			lign = memory[1];
			placement = x - this.getx() -1; 
			System.out.print("1");
		}
		System.out.println(" " + placement );
		
		if(enter) {
			lign[placement]= text;
			if(lign[0] != null && lign[1] != null && lign[0].isOperator() && lign[1].isProperty()) {
				this.getLevel().addPropInMap(((Property)lign[1]).getPropertyEnum() , this.linkId);//ajout prop
				System.out.println("ajout de prop " + this.linkId);
			}
		}
		else {

			if(lign[placement] == text || forceRemove) {
				if(lign[0] != null && lign[1] != null && lign[0].isOperator() && lign[1].isProperty()) {
					
					
					this.getLevel().removePropInMap(((Property)lign[1]).getPropertyEnum(), this.linkId);				;//retirer prop
					System.out.println("supp de prop " + this.linkId);
				}
				lign[placement]= null;
			}

		}
	}
	
	@Override
	public void exiting(int x, int y) {
		notifyMe(x+1, y, memory[0][0], false, true);
		notifyMe(x, y+1, memory[1][0], false, true);
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
	@Override
	public boolean isName() {
		return true;
	}

	
	

}