package babaIsYou;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import babaIsYou.entity.Entity;
import babaIsYou.entity.Name;
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
	 * Function adding the Entity entity to this Cell.
	 * @param entity Entity to add.
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
	 * Function removing the Entity entity to this Cell.
	 * @param entity Entity to remove.
	 * @return true if the removal has been done.
	 * 		   false if not.
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
	 * Check if there is an Entity is STOP in the content of this Cell.
	 * @return true if there is an Entity is STOP in the content of this Cell.
	 * 		   false if not.
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
	 * Function returning list<Entity> of all the entity pushable in the Cell.
	 * @return list<Entity> of the pushable.
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
	 * Check if is possible to enter in the next Cell.
	 * @param direction Direction to move to.
	 * @return true if can.
	 * 		   false if not.
	 */
	public boolean canEnter(Entity entity, DirectionEnum direction) {
		
		if(this.isStop())
			return false;
		if(!this.canBePushed(direction)) 
			return false;
		return true;
	}
	/**Check if it is possible to push the next Cell.
	 * @param direction Direction to push to.
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
	 * Check if the entity can enter the wanted cell.
	 * If it can, proceeds to moves it.
	 * @param entity Entity that moves.
	 * @param direction Direction it moves too.
	 * @return true if can.
	 * 		   false if not.
	 * @throws IOException 
	 */
	public boolean enter(Entity entity, DirectionEnum direction) throws IOException {
		if(!this.canEnter(entity, direction))
			return false;
		List<Entity> copy =  getPushable();		

		for (Entity elem : copy) {
			this.level.pushedIn(elem.getx() + direction.getmoveX(),elem.gety() + direction.getmoveY(),elem, direction);
						
		}
		this.level.removeEntityInCell(entity); 
		this.level.addEntityInCell(entity,x,y);	
		
		return true;
		
	}
	/**
	 * Push entity in the next cell according to direction.
	 * @param entity Entity to move.
	 * @param direction Direction to move to.
	 * @return true if the entity is pushable
	 * 		   false if not
	 * @throws IOException 
	 */
	public boolean pushedIn(Entity entity, DirectionEnum direction) throws IOException {
		if(this.isStop())
			return false;
		List<Entity> copy =  getPushable();

		for (Entity elem : copy) {
			this.level.pushedIn(elem.getx() + direction.getmoveX(),elem.gety() + direction.getmoveY(),elem, direction);
			
			
		}

		this.level.removeEntityInCell(entity);
		this.level.addEntityInCell(entity,x,y);
		this.level.atEnterInCell(entity,x,y);
			
		return true;
	}

	/**
	 * Content getter.
	 * @return Content.
	 */
	public ArrayList<Entity> getContent() {
		return content;
	}

	/**
	 * Content setter.
	 * @param content Content to set it to.
	 */
	public void setContent(ArrayList<Entity> content) {
		this.content = content;
	}
	
	
	

}