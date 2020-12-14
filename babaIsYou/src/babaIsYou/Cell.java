package babaIsYou;

import java.util.ArrayList;
import java.util.List;

import babaIsYou.entity.Entity;
import babaIsYou.entity.entityEnum.DirectionEnum;
 
public class Cell {
	/*une cellule copntient une array list de toute les netité ( truc) présent dessus. 
	 * Elemnt ou Text*/
	ArrayList<Entity> content;
	Level level;
	int x;
	int y;
	
	public Cell( Level level, int x,int y) {
			this.content = new ArrayList<>();
			this.level = level;
			this.x= x;
			this.y= y;
	}
	/**
	 * Function adding the Entity entity from this.Cell
	 * @param entity
	 * @return true if the add has been done
	 * 		   false if not
	 */
	public void add(Entity entity) {
		content.add(entity);
		
	}
	/**
	 * Function removing the Entity entity from this.Cell
	 * @param entity
	 * @return true if the removal has been done
	 * 		   false if not
	 */
	public boolean remove(Entity entity) {
		return content.remove(entity);
		
		
	}
	/**
	 * Check if there is an Entity is STOP in the content of this.Cell
	 * @return true if there is an Entity is STOP in the content of this.Cell
	 * 		   false if not
	 */
	public boolean isStop() {

		for(Entity ent :content) {
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
		for(Entity ent :content) {
			if(ent.isPush()) {
				list.add(ent);
			}
		}
		return list;
		
	}
	
	/**
	 * check if is possible to push entity is the next Cell
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
	
	private boolean canBePushed( DirectionEnum direction) {
		for(Entity e :this.getPushable()) 
			if(!level.canEnter(x + direction.getmoveX(), y + direction.getmoveY(), e,direction))
				return false;
					
			
		return true;
	}
	
	/**
	 * check if is possible to push entity is the next Cell
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
	 * check if it 's possible to add and remove entity in the next case accordingly to direction
	 * @param entity
	 * @param direction
	 * @return
	 */
	public boolean pushedIn(Entity entity, DirectionEnum direction) {
		if(this.isStop())
			return false;
		List<Entity> copy =  getPushable();
		this.level.removeEntityInCell(entity);
		this.level.addEntityInCell(entity,x,y);
		this.level.atEnterInCell(entity,x,y,entity.getid());
		this.level.removeFromToDestroy(level.factory);

		for (Entity elem : copy) {
			this.level.pushedIn(elem.getx() + direction.getmoveX(),elem.gety() + direction.getmoveY(),elem, direction);
			
			
		}
			
		return true;
	}
	
	
	

}
