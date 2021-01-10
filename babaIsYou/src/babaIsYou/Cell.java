package babaIsYou;

import java.util.ArrayList;
import java.util.List;

import babaIsYou.entity.Entity;
import babaIsYou.entity.Name;
import babaIsYou.entity.Operator;
import babaIsYou.entity.entityEnum.DirectionEnum;
 
public class Cell {
	private ArrayList<Entity> content;
	private Level level;
	private ArrayList<Name> listener;
	private int x;
	private int y;
	
	public Cell( Level level, int x,int y) {
		this.setContent(new ArrayList<>());
		this.listener = new ArrayList<>();
		this.level = level;
		this.x = x;
		this.y = y;
	}
	
	public void subscribe(Name name) {
		listener.add(name);
		for (Entity entity : content) {
			if(entity.isText())
				name.notifyMe(this.x, this.y, entity, true);
		}

		
	}
	
	public void unSubscribe(Name name) {
		listener.remove(name);
	}
	
	/**
	 * Function adding the Entity entity to this.Cell
	 * @param entity
	 * @return true if the add has been done
	 * 		   false if not
	 */
	public void add(Entity entity) {
		getContent().add(entity);
		entity.entering(x,y);
		if(entity.isText()) {
			for (Name name : listener) {
				name.notifyMe(this.x, this.y, entity, true);
			}
		}
		
	}
	/**
	 * Function removing the Entity entity to this.Cell
	 * @param entity
	 * @return true if the removal has been done
	 * 		   false if not
	 */
	public boolean remove(Entity entity) {
		entity.exiting(x, y);
		if(entity.isText()) {
			for (Name name : listener) {
				name.notifyMe(this.x, this.y, entity, false);
			}
		}
		
		return getContent().remove(entity);
		
		
	}
	/**
	 * Check if there is an Entity is STOP in the content of this.Cell
	 * @return true if there is an Entity is STOP in the content of this.Cell
	 * 		   false if not
	 */
	public boolean isStop() {

		for(Entity ent :getContent()) {
			if(ent.isStop()) {
				return true;
			}
		}
		return false;
		
	}
	/**
	 * Function returning list<Entity> of all the entity pushable in the this
	 * @return list<Entity>
	 */
	private List<Entity> getPushable() {
		ArrayList<Entity> list  = new ArrayList<>();
		for(Entity ent :getContent()) {
			if(ent.isPush()) {
				list.add(ent);
			}
		}
		return list;
		
	}
	
	/**
	 * check if is possible to enter in the next Cell
	 * @param entity
	 * @param direction
	 * @return true if can
	 * 		   false if not
	 */
	public boolean canEnter(Entity entity, DirectionEnum direction) {
		
		if(this.isStop())
			return false;
		if(!this.canBePushed(direction)) 
			return false;
		return true;
	}
	/**check if is possible to push the next Cell
	 * @param entity
	 * @param direction
	 * @return true if can
	 * 		   false if not
	 */
	private boolean canBePushed( DirectionEnum direction) {
		for(Entity e :this.getPushable()) 
			if(!level.canEnter(x + direction.getmoveX(), y + direction.getmoveY(), e,direction))
				return false;
					
			
		return true;
	}
	
	/**
	 * check the enter on the next cell has been successful
	 * it will notice the cell of the moovement (in the case of entity is text)
	 * @param entity
	 * @param direction
	 * @return true if can
	 * 		   false if not
	 */
	public boolean enter(Entity entity, DirectionEnum direction) {
		if(!this.canEnter(entity, direction))
			return false;
		List<Entity> copy =  getPushable();
		this.level.removeEntityInCell(entity); 
		this.level.addEntityInCell(entity,x,y);			

		for (Entity elem : copy) {
			this.level.pushedIn(elem.getx() + direction.getmoveX(),elem.gety() + direction.getmoveY(),elem, direction);
						
		}
		
		return true;
		
	}
	/**
	 * push entity in the next cell accordingly to direction
	 * @param entity
	 * @param direction
	 * @return true if the entity is pushable
	 * 		   false if not
	 */
	public boolean pushedIn(Entity entity, DirectionEnum direction) {
		if(this.isStop())
			return false;
		List<Entity> copy =  getPushable();
		this.level.removeEntityInCell(entity);
		this.level.addEntityInCell(entity,x,y);
		this.level.atEnterInCell(entity,x,y,entity.getEntityId());

		for (Entity elem : copy) {
			this.level.pushedIn(elem.getx() + direction.getmoveX(),elem.gety() + direction.getmoveY(),elem, direction);
			
			
		}
			
		return true;
	}

	public ArrayList<Entity> getContent() {
		return content;
	}

	public void setContent(ArrayList<Entity> content) {
		this.content = content;
	}
	
	
	

}


