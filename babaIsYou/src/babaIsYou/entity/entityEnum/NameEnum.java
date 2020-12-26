package babaIsYou.entity.entityEnum;

public enum NameEnum {
	Baba(100,100),Flag(101,101),Wall(102,102),
	Water(103,103),Skull(104,104),Lava(105,105),
	Rock(106,106);
	 
	private final int imageID;
	private final int ElemID;
	
	NameEnum(int idImg, int idElem) {
        this.imageID = idImg;  
        this.ElemID = idElem;  
             
   }

	public int getimageID() {
		return imageID;
	}

	public int getElemID() {
		return ElemID;
	}
   
}
