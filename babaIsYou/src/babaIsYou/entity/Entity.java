package babaIsYou.entity;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.EventBabaGame;
import babaIsYou.entity.entityEnum.PropertyEnum;

import java.util.List;

public abstract class Entity {
	/*each entity have x and y linked to the number of the cell place in the level)*/
	/*chaque entity a un image ID ( correpondant au numero d'image qu'on va associé)*/
	private final int imageId;
	private int x;
	private int y;
	private final int entityId;
	private final Level level;
	public boolean isText() {
		return false;
	}
	
	public Entity(int imageID, int entityId, Level level) {
		this.imageId = imageID;
		this.entityId = entityId;
		this.level = level;
	}
	
	public Level getLevel() {
		return this.level;
	}

	public int getEntityId() {
		return this.entityId;
	}

	public int getImageId() {
		return this.imageId;
	}
	/*x getteur*/
	public int getx() {
		return this.x;
	}
	/*x setteur*/
	public void setx(int x2) {
		this.x = x2;
	}
	
	/*y getteur*/
	public int gety() {
		return this.y;
	}
	/*x setteur*/
	public void sety(int y2) {
		this.y = y2;
	}
	
	public void isRemove(int x, int y) {
		//TODO:
	}
	
	public void enter(int x, int y) {
		//TODO:
	}
	
	public void updateProp() {
		//TODO:
	}
	
	public EventBabaGame isEnteredBy(Entity newElem) {
		List<PropertyEnum> props = this.getPropertys();
		if(props.contains(PropertyEnum.Defeat))
			return EventBabaGame.Defeat;
		if(props.contains(PropertyEnum.Sink))
			return EventBabaGame.DestroyAll;
		if(props.contains(PropertyEnum.Hot) && newElem.getPropertys().contains(PropertyEnum.Melt))
			return EventBabaGame.Destroy;
		if(props.contains(PropertyEnum.Win))
			return EventBabaGame.Win;
		return EventBabaGame.Good;
	}
	protected abstract List<PropertyEnum> getPropertys();
//	public abstract boolean isMelt();
//	public abstract boolean isHot();
//	public abstract boolean isSink();
//	public abstract boolean isDefeat();
//	public abstract boolean isWin();
	
	
	public abstract boolean isPush();
	public abstract boolean isStop();
	
	
}
