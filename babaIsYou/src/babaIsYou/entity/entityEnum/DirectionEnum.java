package babaIsYou.entity.entityEnum;

/**
 * Direction are represented as a direction vector
 *
 */
public enum DirectionEnum {
	
	RIGHT(1,0), LEFT(-1,0), UP(0,-1), DOWN(0,1) ;
	
	private final int moveX;
	private final int moveY;
	
	DirectionEnum(int moveX, int moveY) {
        this.moveX = moveX;  
        this.moveY = moveY;  
             
   }
	/**
	 * Getter of the x vector movement
	 * @return the x vector movement
	 */
	public int getmoveX() {
		return moveX;
	}

	/**
	 * Getter of the y vector movement
	 * @return the y vector movement
	 */
	public int getmoveY() {
		return moveY;
	}
	

}
