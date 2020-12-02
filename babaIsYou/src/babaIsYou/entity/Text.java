package babaIsYou.entity;

public abstract class Text extends Entity {

	public Text(int imageID) {
		super(imageID);
	}
	@Override
	public final boolean isPush() {
		return true;
	}
	
	public final boolean isStop() { // on peut tjrs pousser un text
		return false;
	}
	
	@Override
	public boolean isText() {
		return true;
	}

}
