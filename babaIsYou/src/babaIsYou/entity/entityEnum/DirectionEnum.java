package babaIsYou.entity.entityEnum;

public enum DirectionEnum {
	/*chaque direction est représenter par un "vecteur" xy->
	 * pratique pour gerer le mouvement*/
	RIGHT(1,0), LEFT(-1,0), UP(0,-1), DOWN(0,1) ;
	
	private final int moveX;
	private final int moveY;
	
	DirectionEnum(int moveX, int moveY) {
        this.moveX = moveX;  
        this.moveY = moveY;  
             
   }

	public int getmoveX() {
		return moveX;
	}

	public int getmoveY() {
		return moveY;
	}
	

}
