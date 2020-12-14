package babaIsYou.entity;

import babaIsYou.Level;

public class Name extends Text {
	private int objectId;

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
	public void notifyMe(int x, int y, Text text, boolean enter) {
		//TODO:
	}

}
