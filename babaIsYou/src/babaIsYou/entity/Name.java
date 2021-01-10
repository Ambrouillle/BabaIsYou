package babaIsYou.entity;

import babaIsYou.Level;

public class Name extends Text {
	private final int objectId;
	private int linkId;
	private Entity[][] memory;
	

	protected Name(int imageID,int id,int objectID,int linkId,Level level) {
		super(imageID, id,level);
		this.objectId = objectID;
		this.linkId = linkId;
		memory = new Entity[2][2];
	}
	
	/**
	 * Notifies the name in case something changes in the neighbours.
	 * @param x
	 * @param y
	 * @param text
	 * @param enter
	 */
	public void notifyMe(int x, int y, Entity text, boolean enter) {
		System.out.println("x , y = "+x+" "+y);	
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

			if(lign[0] != null && lign[1] != null && lign[0].isOperator() && lign[1].isProperty()) {
				
		
				this.getLevel().removePropInMap(((Property)lign[1]).getPropertyEnum(), this.linkId);				;//retirer prop
				System.out.println("supp de prop " + this.linkId);
			}
			lign[placement]= null;

		}
	}

	@Override
	public void exiting(int x, int y) {
		notifyMe(x+1, y, this, false);
		notifyMe(x, y+1, this, false);
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


}
