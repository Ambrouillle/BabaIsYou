package babaIsYou.entity;

import babaIsYou.Level;

public abstract class Operator extends Text {
	private int objectId;
	
	public Operator(int imageID ,int id,int objectID, Level level, String name) {
		super(imageID, id,level, name);
		this.objectId = objectID;
		
		
	}
	@Override
	public void isRemove(int x, int y) {
//		this.getLevel().unSubcribeTo(this, x+1, y);
//		this.getLevel().unSubcribeTo(this, x-1, y);
//		this.getLevel().unSubcribeTo(this, x, y+1);
//		this.getLevel().unSubcribeTo(this, x, y-1);
	}
	
	@Override
	public void enter(int x, int y) {
//		this.getLevel().subcribeTo(this, x+1, y);
//		this.getLevel().subcribeTo(this, x-1, y);
//		this.getLevel().subcribeTo(this, x, y+1);
//		this.getLevel().subcribeTo(this, x, y-1);
	}
}
