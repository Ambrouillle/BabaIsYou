package babaIsYou.entity.entityEnum;

public enum NameEnum {
	Baba(100,100,400),Flag(101,101,401),Wall(102,102,402),
	Water(103,103,403),Skull(104,104,404),Lava(105,105,405),
	Rock(106,106,406);
	 
	private final int imageID;
	private final int elemID;
	private final int linkID;
	
	NameEnum(int idImg, int idElem, int linkId) {
        this.imageID = idImg;  
        this.elemID = idElem;  
        this.linkID = linkId;
   }
	/**
	 * Getter for imageID
	 * @return this.imageID
	 */
	public int getimageID() {
		return imageID;
	}

	/**
	 * Getter for ElemID
	 * @return this.ElemID
	 */
	public int getElemID() {
		return elemID;
	}
	
	public int getLinkId() {
		return this.linkID;
	}
   
}
