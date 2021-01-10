package babaIsYou.entity;

import babaIsYou.Level;

public abstract class Operator extends Text {

	public Operator(int imageID, int id, Level level) {
		super(imageID, id,level);


	}
	
	
	@Override
	public boolean isOperator() {
		return true;
	}

}
