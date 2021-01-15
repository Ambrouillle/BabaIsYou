package babaIsYou.entity.entityEnum;

public enum OperatorEnum {
	Is(200,200);
	
	private final int imageID;
	private final int ElemID;
	
	OperatorEnum(int idImg, int idElem) {
        this.imageID = idImg;  
        this.ElemID = idElem;  
             
   }
	/**
	 * Getter imageId
	 * @return this.imageId
	 */
	public int getimageID() {
		return imageID;
	}

	/**
	 * Getter ElemID
	 * @return this.ElemID
	 */
	public int getElemID() {
		return ElemID;
	}
	
	}

