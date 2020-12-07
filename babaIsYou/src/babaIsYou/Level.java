package babaIsYou;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.Property;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.Event;
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
	 * @param entity
	 * @param direction
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
	public boolean moove(Entity entity, DirectionEnum direction,EntityFactory factory) {
		int x = entity.getx()+direction.getmoveX();
		int y = entity.gety()+direction.getmoveY();
		if(testOutOfBound(x, y))
			return false;
		Event EventRes = atEnterInCell(entity, x, y, factory) ;
		if(EventRes != Event.Stop) {
			
		}
		switch(EventRes) {
		case Defeat:
			System.out.println("DEFEAT");
			return false;
		case Win:
			System.out.println("Win");
			return true;		
		case Good:
			return this.plateau[x][y].enter(entity, direction);
		default :
			break;
		}
		return false;
	}
	/**
	 * Function that return the idElem of the Object with the PropertyEnum prop
	 * @param push
	 * @return the idElem of the object according to the prop
	 */
	public Integer getElemnwithProp(PropertyEnum prop) {
		for (Map.Entry mapentry : propertyHashMap.entrySet()) {
	           if(((ArrayList<PropertyEnum>) mapentry.getValue()).contains(prop)) {
	        	   return (Integer) mapentry.getKey();
	           }
	        }
		return 0;
		}
		
	
	/**
	 * fonction that moove every instance of Level with the PropertyEnum prop
	 * will be used to moove every instance with You
	 * @param factory: to have the ElementHashMap of all created instances
	 * @param prop
	 * @param direction
	 * @return
	 */
	public boolean mooveProp(EntityFactory factory,PropertyEnum prop,DirectionEnum direction) {
		Integer idElem = getElemnwithProp(prop);
		if(idElem != 0) {
			ArrayList<Element> list = factory.elementHashMap.get(idElem);
			for(Element el : list) {
				moove(el,direction,factory);
			}
			return true;
		}
		
		return false;
		
	}
	/**
	 * Function that destroy an element from everywhere
	 * @param factory
	 * @param ent
	 */
	public void removeEntityfromEveryWhere(EntityFactory factory, Entity ent) {
		if(!ent.isText()) {
			//remove ent from the factory
			if(factory.elementHashMap.containsKey(((Element) ent).getElemID())) {
				ArrayList list = factory.elementHashMap.get(((Element) ent).getElemID());
				if(list.size() == 1) {
					//check if there is a prop associed and remove it from level.propertyHashMap
					if(this.propertyHashMap.containsKey(((Element) ent).getElemID())) {
						this.propertyHashMap.remove(((Element) ent).getElemID()) ; 
					}
				}
				list.remove(ent);
				factory.elementHashMap.put(((Element) ent).getElemID(), list);
			}
			//remove ent from cell
			removeEntityInCell(ent);
			
		}
	}
	/*
	public boolean whatIsWin(EntityFactory factory) {		
		Integer WinElem = getElemnwithProp(PropertyEnum.Win);
		Integer YouElem = getElemnwithProp(PropertyEnum.You);
		if(WinElem != 0) {// no element have the Win property
			//check if every instance of Player is in the same Cell as WinElem
			ArrayList<Element> listWin = factory.elementHashMap.get(WinElem);
			ArrayList<Element> listYou = factory.elementHashMap.get(YouElem);
			for(Element elyou : listYou) {
				
			}
			}
			
		}
		return true;
	}
	*/
	
	
	public Event atEnterInCell(Entity entity,int x, int y,EntityFactory factory) {
		//check what happend when entity will go to the Cell x,y
		//if win
		Integer WinElem = getElemnwithProp(PropertyEnum.Win);
		Integer YouElem = getElemnwithProp(PropertyEnum.You);
		if(!entity.isText()) {//if ent if an Element			
			Integer elemDefeat = getElemnwithProp(PropertyEnum.Defeat);
			Integer elemYou = getElemnwithProp(PropertyEnum.You);
			Integer elemWin = getElemnwithProp(PropertyEnum.Win);
			Integer elemSink = getElemnwithProp(PropertyEnum.Sink);
			Integer elemStop = getElemnwithProp(PropertyEnum.Stop);
			Integer elemHot = getElemnwithProp(PropertyEnum.Hot);
			Integer elemMelt = getElemnwithProp(PropertyEnum.Melt);
			
			for(Entity entiCell : this.plateau[x][y].content) {
				//stop
				if(((Element)entiCell).getElemID() == elemStop) {
					return Event.Stop;
				}
				
				//Defeat
				if(((Element) entity).getElemID() == elemYou && ((Element)entiCell).getElemID() == elemDefeat) {
					
					return Event.Defeat;
				}

				//Win
				if(((Element) entity).getElemID() == elemYou && ((Element)entiCell).getElemID() == elemWin) {
					return Event.Win;
				}
				//SINK
				if(((Element) entiCell).getElemID() == elemSink) {
					//destroy of the entity 
					//if entity is only instance of You == DEFEAT
					if(((Element) entity).getElemID() == elemYou) {
						//is only instance?

						ArrayList<Element> list = factory.elementHashMap.get(((Element) entity).getElemID());
						if(list.size() == 1) {
							return Event.Defeat;							
						}						
					}
					removeEntityfromEveryWhere(factory, entity);
					return Event.Good;
				}
				//Melt
				if(((Element) entiCell).getElemID() == elemHot && ((Element) entity).getElemID() == elemMelt) {
					//destroy of the entity 
					//if entity is only instance of You == DEFEAT
					if(((Element) entity).getElemID() == elemYou) {
						//is only instance?

						ArrayList<Element> list = factory.elementHashMap.get(((Element) entity).getElemID());
						if(list.size() == 1) {
							return Event.Defeat;							
						}						
					}
					removeEntityfromEveryWhere(factory, entity);
					return Event.Good;
				}
				
				
			}
			return Event.Good;
			
			
		}
		return Event.Good;
		
	}

	

}