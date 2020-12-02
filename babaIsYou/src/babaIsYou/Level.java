package babaIsYou;

import java.util.ArrayList;

import java.util.HashMap;
import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.Property;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.PropertyEnum;

/*cahque level contyient un tableau de cell : plateau
 * et une liste de propriété associé a des objets.:
 * ex s'il y a alligne sur la map : rock is puck alors dans propertyHashMap
 * il y aura (rock.getElemID(),[push] )
 * si en plus il y a rock is sink alors :
 * (rock.getElemID(),[push,sink] )
 * */
public class Level {
	public Cell[][] plateau;
	public HashMap<Integer,ArrayList<PropertyEnum>> propertyHashMap ; 	
	
	public Level(int x, int y) {
		plateau = new Cell[x][y];
		for(int i = 0 ; i < x ; i++) {
			for(int j = 0 ; j < y ; j++) {
				plateau[i][j] = new Cell(this,i,j);
			}
		}
		propertyHashMap = new HashMap<Integer,ArrayList<PropertyEnum>>() ;
	}
	
	/*ajouter l'entity entitiy dans la cell[x][y]* et modifie  les champs x et y de entity.*/
	public void addEntityInCell(Entity entity,int x, int y) {
		if(testOutOfBound(x, y))
			throw new RuntimeException("element placed outside of level");
		entity.setx(x);
		entity.sety(y);
		plateau[x][y].add(entity);//add
	}

	private boolean testOutOfBound(int x, int y) {
		if(x >= plateau.length || y >= plateau[0].length || x < 0 || y < 0)
			return true;
		return false;
	}
	/*enleve l'entity entitiy dans la cell[x][y]. Comme l'objet n'est plus acceccible nul part .
	 * Il disparait*/
	public void removeEntityInCell(Entity entity) {
		plateau[entity.getx()][entity.gety()].remove(entity);
	}
	
	
	
	/*ajouter la propriete prop dans propertyHashMap de this .
	 * rappelle propertyHashMap<idElement, [prop]> */
	public void addPropInMap(PropertyEnum prop, int idElement ) {
		ArrayList list;
		if(this.propertyHashMap.containsKey(idElement)) {
			list = this.propertyHashMap.get(idElement);
			if(!list.contains((prop))){
				list.add(prop);
				}
		}
		else { 
			System.out.println("test");
			list = new ArrayList();
			list.add(prop);
			this.propertyHashMap.put(idElement,list);
		}
		
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param entity
	 * @param direction
	 * @return
	 */
	public boolean pushedIn(int x,int y,Entity entity, DirectionEnum direction) {

		if(testOutOfBound(x, y))
			return false;
		return this.plateau[x][y].pushedIn(entity, direction);	
	
	}
	/**
	 * remove the Property prop accordingly to Element elem in this.Level
	 * @param prop
	 * @param elem
	 */
	public void removePropInMap(Property prop,Element elem ) {
		if(this.propertyHashMap.containsKey(elem)) {
			if(this.propertyHashMap.get(elem).contains(prop)) {
				this.propertyHashMap.get(elem).remove(prop);
			}
		}
	}

	/**
	 *  check if there is any object STOP is the Cell[x][y]or if the Cell is out of the map
	 * @return true if there is an object STOP
	 * 		   false  if not
	 */
	public boolean isCaseisStop(int x, int y) {
		if(testOutOfBound(x, y))
			return true;
		return this.plateau[x][y].isStop();
	}
	
	/**
	 * Check if the entity can be push in the Cell[x][y]
	 * @param x
	 * @param y
	 * @param e
	 * @param d
	 * @return
	 */
	public boolean canEnter( int x,int y, Entity entity, DirectionEnum direction) {
		if(testOutOfBound(x, y)) {
			return false;
		}
		return this.plateau[x][y].canEnter(entity, direction);
	}
	/**
	 * fonction that moove the Entity entity in the Direction
	 * @param entity
	 * @param direction
	 * @return true if the object can be mooved
	 * 		   false if not
	 */
	public boolean moove(Entity entity, DirectionEnum direction) {
		int x = entity.getx()+direction.getmoveX();
		int y = entity.gety()+direction.getmoveY();
		if(testOutOfBound(x, y))
			return false;
		//if(isInTheLevel(entity,direction) && !(isNextStop(entity,direction))) {			
			return this.plateau[x][y].enter(entity, direction);
	}

	

}