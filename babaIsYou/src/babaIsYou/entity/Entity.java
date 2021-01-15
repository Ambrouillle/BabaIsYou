package babaIsYou.entity;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.EventBabaGame;
import babaIsYou.entity.entityEnum.PropertyEnum;

import java.util.List;

public abstract class Entity {
	/*each entity have x and y linked to the number of the cell place in the level)*/
	private final int imageId;
	private int x;
	private int y;
	private final int entityId;
	private final Level level;

	private String entityName;
	
	public boolean isText() {
		return false;
	}
	
	public boolean isOperator() {
		return false;
	}
	
	public boolean isProperty() {
		return false;
	}
	
	public Entity(int imageID, int entityId, Level level) {
		this.imageId = imageID;
		this.entityId = entityId;
		this.level = level;
	}
	public String getEntityName(){
		return this.entityName;
		}
	
	/**
	 * Getter this.level
	 * @return this.level
	 */
	public Level getLevel() {
		return this.level;
	}

	/**
	 * Getter this.entityId
	 * @return this.entityId
	 */
	public int getEntityId() {
		return this.entityId;
	}

	/**
	 * Getter this.imageId
	 * @return this.imageId
	 */
	public int getImageId() {
		return this.imageId;
	}
	/**
	 * Getter this.x
	 * @return this.x
	 */
	public int getx() {
		return this.x;
	}
	
	/**
	 * Setter this.x
	 * @return this.x
	 */
	public void setx(int x2) {
		this.x = x2;
	}
	
	/**
	 * Getter this.y
	 * @return this.y
	 */
	public int gety() {
		return this.y;
	}
	/**
	 * Setter this.y
	 * @return this.y
	 */
	public void sety(int y2) {
		this.y = y2;
	}
	
	public void exiting(int x, int y) {
			}
	
	public void entering(int x, int y) {
			}
	
	public void explode(){
		for(int i = x-1; i <= x+1;i++){
			for(int j = y-1; j <= y+1;j++){
				level.clearCell(i,j);
			}
		}
	}
	
	public EventBabaGame isEnteredBy(Entity newElem) {
		List<PropertyEnum> props = this.getProperties();
		if(props == null)
			return EventBabaGame.Good;
		if(props.contains(PropertyEnum.Defeat) && newElem.isYou() )
			return EventBabaGame.Defeat;
		if(props.contains(PropertyEnum.Sink))
			return EventBabaGame.DestroyAll;
		if(props.contains(PropertyEnum.Hot) && newElem.getProperties().contains(PropertyEnum.Melt))
			return EventBabaGame.Destroy;
		if(props.contains(PropertyEnum.Win))
			return EventBabaGame.Win;
		if(props.contains(PropertyEnum.Detonate))
			return EventBabaGame.Explode;
		return EventBabaGame.Good;
	}
	protected abstract List<PropertyEnum> getProperties();
	
	public abstract boolean isYou();
	public abstract boolean isPush();
	public abstract boolean isStop();

	public boolean isName() {
		return false;
	}
	
	
}