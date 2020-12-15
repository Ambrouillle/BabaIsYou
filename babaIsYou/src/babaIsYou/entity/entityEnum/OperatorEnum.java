package babaIsYou.entity.entityEnum;

public enum OperatorEnum {
	Is(200,200) ;//ajouter plus tard on , has,and
	private final int imageID;
	private final int ElemID;
	
	private OperatorEnum(int idImg,int idElem) {  
        this.imageID = idImg;  
        this.ElemID = idElem;  
             
   }

	public int getimageID() {
		return imageID;
	}

	public int getElemID() {
		return ElemID;
	}
	
	};

