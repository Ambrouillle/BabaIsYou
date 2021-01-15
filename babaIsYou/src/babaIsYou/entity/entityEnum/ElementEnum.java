package babaIsYou.entity.entityEnum;

public enum ElementEnum {
	Baba(400,400),Flag(401,401),Wall(402,402),Water(403,403),
	Skull(404,404),	Lava(405,405),Rock(406,406),Bomb(407,407);
	
	private final int imageID;
	private final int elemID;
	
	ElementEnum(int idImg, int idElem) {
        this.imageID = idImg;  
        this.elemID = idElem;  
             
   }
	/**
	 * Getter of imageID
	 * @return this.imageID
	 */
	public int getimageID() {
		return imageID;
	}

	/**
	 * Getter of elemID
	 * @return this.elemID
	 */
	public int getElemID() {
		return elemID;
	}
	
}
