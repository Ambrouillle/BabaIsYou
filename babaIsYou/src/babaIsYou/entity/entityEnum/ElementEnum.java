package babaIsYou.entity.entityEnum;

import babaIsYou.Level;

public enum ElementEnum {
	Baba(400,400),Flag(401,401),Wall(402,402),Water(403,403),
	Skull(404,405),	Lava(405,405),Rock(406,406);
	
	private  int imageID;
	private int ElemID;
	
	private ElementEnum(int idImg,int idElem) {  
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
