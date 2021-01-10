package babaIsYou.entity;

import babaIsYou.Level;

public abstract class Operator extends Text {
	private final int objectId;
	
	public Operator(int imageID ,int id,int objectID, Level level) {
		super(imageID, id,level);
		this.objectId = objectID;
		
		
	}
	
	
	@Override
	public boolean isOperator() {
		return true;
	}

}
