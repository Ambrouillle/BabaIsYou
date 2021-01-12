package babaIsYou.entity.entityEnum;

public enum PropertyEnum {
	You(300,300),Win(301,301),Stop(302,302),Push(303,303),Melt(304,304),
	Hot(305,305),Defeat(306,306),Sink(307,307),Explode(308,308);
	
	private final int imageID;
	private final int ElemID;
	
	PropertyEnum(int idImg, int idElem) {
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
