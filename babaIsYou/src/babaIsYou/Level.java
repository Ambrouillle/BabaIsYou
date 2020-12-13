package babaIsYou;

import java.io.*;
import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.Property;
import babaIsYou.entity.entityEnum.*;

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
	public ArrayList<Integer> toDestroy = new ArrayList<>();

	public Level(int x, int y) {
		plateau = new Cell[x][y];
		for(int i = 0 ; i < x ; i++) {
			for(int j = 0 ; j < y ; j++) {
				plateau[i][j] = new Cell(this,i,j);
			}
		}
		propertyHashMap = new HashMap<>() ;
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
		return x >= plateau.length || y >= plateau[0].length || x < 0 || y < 0;
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
	public boolean moove(Entity entity, DirectionEnum direction) {
		int x = entity.getx()+direction.getmoveX();
		int y = entity.gety()+direction.getmoveY();
		if(testOutOfBound(x, y))
			return false;
		//if(isInTheLevel(entity,direction) && !(isNextStop(entity,direction))) {			
			return this.plateau[x][y].enter(entity, direction);
	}
	/**
	 * 
	 * @param prop
	 * @return the idElem of the object according to the prop
	 */
	public Object getElemnwithProp(PropertyEnum prop) {
		for (Map.Entry mapentry : propertyHashMap.entrySet()) {
	           if(((ArrayList<PropertyEnum>) mapentry.getValue()).contains(prop)) {
	        	   return mapentry.getKey();
	           }
	        }
		return null;
		}

	
	public boolean mooveProp(EntityFactory factory,PropertyEnum prop,DirectionEnum direction) {
		Integer idElem = (Integer) getElemnwithProp(prop);
		if(prop != null) {
			ArrayList<Element> list = factory.elementHashMap.get(idElem);
			for(Element el : list) {
				moove(el,direction);
			}
			return true;
		}
		
		return false;
		
	}
	
	public void removeEntityfromEveryWhere(EntityFactory factory, Entity ent) {
		if(ent instanceof  Element) {
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
	void LoadLevel(int levelNumber, EntityFactory factory){
		try {
			String row;
			String filepath = "Ressources" + File.separator + levelNumber + ".csv";
			BufferedReader csvReader = new BufferedReader(new FileReader(filepath));
			while((row = csvReader.readLine()) != null){
				String[] data = row.split(",");
				switch (atoi(data[0])){
					case 1:
						this.addEntityInCell(factory.create(ElementEnum.valueOf(data[1])),atoi(data[2]),atoi(data[3]));
						break;
					case 2:
						this.addEntityInCell(factory.create(PropertyEnum.valueOf(data[1])),atoi(data[2]),atoi(data[3]));
						break;
					case 3:
						this.addEntityInCell(factory.create(NameEnum.valueOf(data[1])),atoi(data[2]),atoi(data[3]));
						break;
				//	case 4:
				//		factory.create(OperatorEnum.valueOf(data[1]));
				}

			}
		}
		catch(IOException ex){
			System.err.println("An exception occured");
			ex.printStackTrace();
		}
	}
	int atoi(String nb){
		int result = 0;
		for (int i = 0; i < nb.length(); i++)
			result = result * 10 + nb.charAt(i) - '0';
		return result;
	}

	public Events isLost(EntityFactory factory){
		Integer idElem = (Integer) getElemnwithProp(PropertyEnum.You);

		ArrayList<Element> list = factory.elementHashMap.get(idElem);
		System.out.println(list);
		if (list == null){
			return Events.Defeat;
		}
		return Events.Good;
	}

	public Events atEnterInCell(Entity entity,int x, int y,EntityFactory factory,int id) {
		//check what happend when entity will go to the Cell x,y
		//if win
		Events ev;
		Entity toRemove;
		toRemove = null;
		if(!entity.isText()) {//if ent is an Element
			Integer elemDefeat = (Integer) getElemnwithProp(PropertyEnum.Defeat);
			Integer elemYou = (Integer) getElemnwithProp(PropertyEnum.You);
			Integer elemWin = (Integer) getElemnwithProp(PropertyEnum.Win);
			Integer elemSink = (Integer) getElemnwithProp(PropertyEnum.Sink);
			Integer elemStop = (Integer) getElemnwithProp(PropertyEnum.Stop);
			Integer elemHot = (Integer) getElemnwithProp(PropertyEnum.Hot);
			Integer elemMelt = (Integer) getElemnwithProp(PropertyEnum.Melt);

			for(Entity entiCell : this.plateau[x][y].content) {
				//stop
				if (elemStop != null) {
					if (((Element) entiCell).getElemID() == elemStop) {
						return Events.Stop;
					}
				}

				//Defeat
				if (elemDefeat != null) {
					if (((Element) entity).getElemID() == elemYou && ((Element) entiCell).getElemID() == elemDefeat) {
						System.out.println("Defeat");
						return Events.Defeat;
					}
				}

				//Win
				if (elemWin != null) {
					if (((Element) entity).getElemID() == elemYou && ((Element) entiCell).getElemID() == elemWin) {
						return Events.Win;
					}
				}
				//SINK
				if (elemSink != null) {
					if (((Element) entiCell).getElemID() == elemSink) {
						toRemove = entiCell;
						//destroy of the entity
						//if entity is only instance of You == DEFEAT
						this.toDestroy.add(id);
					}
				}
				//Melt
				if (elemMelt != null) {
					if (((Element) entiCell).getElemID() == elemHot && ((Element) entity).getElemID() == elemMelt) {
						//destroy of the entity
						//if entity is only instance of You == DEFEAT
						this.toDestroy.add(id);
					}
				}
			}
			if (toRemove != null) {
				removeEntityfromEveryWhere(factory, toRemove);
			}
		}
		ev = isLost(factory);
		return ev;
	}
}