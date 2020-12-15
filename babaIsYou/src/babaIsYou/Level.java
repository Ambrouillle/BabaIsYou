package babaIsYou;

import java.io.*;

import java.util.*;


import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.Operator;
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
	private HashMap<Integer,ArrayList<PropertyEnum>> propertyHashMap ; 	
	public ArrayList<Integer> toDestroy = new ArrayList<>();
	public EntityFactory factory;
	public int x;
	public int y;

	public Level(int id) {
		factory = new EntityFactory(this);
		this.loadLevel(id, factory);
	}
	
	
	
	public boolean subcribeTo(Operator operator, int x,int y) {
//		if(testOutOfBound(x, y))
//			return false;
//		subcribeTo(operator, x, y);
		return true;
	}
	
	public void unSubcribeTo(Operator operator, int x,int y) {
//		if(testOutOfBound(x, y))
//			return;
//		this.plateau[x][y].unSubscribe(operator);
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
		if(this.getPropertyHashMap().containsKey(idElement)) {
			list = this.getPropertyHashMap().get(idElement);
			if(!list.contains((prop))){
				list.add(prop);
				}
		}
		else {
			list = new ArrayList();
			list.add(prop);
			this.getPropertyHashMap().put(idElement,list);
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
		if(this.getPropertyHashMap().containsKey(elem)) {
			if(this.getPropertyHashMap().get(elem).contains(prop)) {
				this.getPropertyHashMap().get(elem).remove(prop);
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
	public EventBabaGame moove(Entity entity, DirectionEnum direction) {
		int x = entity.getx()+direction.getmoveX();
		int y = entity.gety()+direction.getmoveY();
		if(testOutOfBound(x, y))
			return EventBabaGame.Stop;
		//if(isInTheLevel(entity,direction) && !(isNextStop(entity,direction))) {
		if( this.plateau[x][y].enter(entity, direction))
			return atEnterInCell(entity, x, y, entity.getEntityId());
		return EventBabaGame.Stop;
	}
	/**
	 * 
	 * @param prop
	 * @return the idElem of the object according to the prop
	 */
	public List<Integer> getElemnwithProp(PropertyEnum prop) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (Map.Entry mapentry : getPropertyHashMap().entrySet()) {
	           if(((ArrayList<PropertyEnum>) mapentry.getValue()).contains(prop)) {
	        	   list.add((Integer) mapentry.getKey());
	           }
	        }
		return list;
		}

	
	public boolean mooveProp(EntityFactory factory,PropertyEnum prop,DirectionEnum direction) {
		ArrayList<Element> list =new ArrayList<>();
		for (int idElem  :getElemnwithProp(prop)) {
			list.addAll(factory.elementHashMap.get(idElem));
		}
		if(prop != null) {
			for(Element el : list) {
				moove(el,direction);
			}
			return true;
		}
		
		return false;
		
	}
	
	public void removeEntityfromEveryWhere(EntityFactory factory, Entity ent) {
		if (ent == null) return;
		if(ent instanceof  Element) {
			//remove ent from the factory
			if(factory.elementHashMap.containsKey(((Element) ent).getElemID())) {
				ArrayList list = factory.elementHashMap.get(((Element) ent).getElemID());
				if(list.size() == 1) {
					//check if there is a prop associed and remove it from level.propertyHashMap
					if(this.getPropertyHashMap().containsKey(((Element) ent).getElemID())) {
						this.getPropertyHashMap().remove(((Element) ent).getElemID()) ; 
					}
				}
				list.remove(ent);
				factory.elementHashMap.put(((Element) ent).getElemID(), list);
			}
			//remove ent from cell
			removeEntityInCell(ent);
			
		}
	}

	public void spawnEntity(EntityFactory factory,String[] data){
		switch (atoi(data[0])){
			case 1://Element
				if (atoi(data[4]) == 1){
					String[] x_val = data[2].split("-");
					String[] y_val = data[3].split("-");
					for(int i = atoi(x_val[0]);i <= atoi(x_val[1]); i++){
						for (int j = atoi(y_val[0]);j <= atoi(y_val[1]); j++)
							this.addEntityInCell(factory.create(ElementEnum.valueOf(data[1])), i, j);
					}
				} else
					this.addEntityInCell(factory.create(ElementEnum.valueOf(data[1])), atoi(data[2]), atoi(data[3]));
				break;
			case 2://Property
				this.addEntityInCell(factory.create(PropertyEnum.valueOf(data[1])),atoi(data[2]),atoi(data[3]));
				break;
			case 3://Name
				this.addEntityInCell(factory.create(NameEnum.valueOf(data[1])),atoi(data[2]),atoi(data[3]));
				break;
			//	case 4://Not Working Operator
			//		factory.create(OperatorEnum.valueOf(data[1]));
		}
	}

	public void removeFromToDestroy(EntityFactory factory){
		Entity target = null;
		for(Integer id : toDestroy){
			for(ArrayList<Element> list :factory.elementHashMap.values()){
				for(Element e : list){
					if (e.getEntityId() == id){
						target = e;
					}
				}
			}
			removeEntityfromEveryWhere(factory, target);
		}
	}
	public void loadLevel(int levelNumber, EntityFactory factory){
		try {
			String row;
			String filepath = "Ressources" + File.separator + levelNumber + ".csv";
			BufferedReader csvReader = new BufferedReader(new FileReader(filepath));
			row = csvReader.readLine();
			String[] data = row.split(",");
			this.x = atoi(data[0]);
			this.y = atoi(data[1]);
			plateau = new Cell[this.x][this.y];
			for(int i = 0 ; i < x ; i++) {
				for(int j = 0 ; j < y ; j++) { plateau[i][j] = new Cell(this,i,j); }
			}
			propertyHashMap = new HashMap<>() ;
			while((row = csvReader.readLine()) != null){
				data = row.split(",");
				spawnEntity(factory, data);
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

	public EventBabaGame isLost(EntityFactory factory){
		ArrayList<Element> list =new ArrayList<>();
		for (int idElem  :getElemnwithProp(PropertyEnum.You)) {
			list.addAll(factory.elementHashMap.get(idElem));
		}

		if (list == null|| list.isEmpty()){
			return EventBabaGame.Defeat;
		}
		return EventBabaGame.Good;
	}

	public EventBabaGame atEnterInCell(Entity entityEntering,int x, int y,int id) {
		//check what happen when entity will go to the Cell x,y
		//if win
		if(testOutOfBound(x,y)){
			return EventBabaGame.Stop;
		}
		List<EventBabaGame> listEvent = new ArrayList<>();
		
		for(Entity entiCell : this.plateau[x][y].content) {
			listEvent.add(entiCell.isEnteredBy(entityEntering));
		}
		if(listEvent.contains(EventBabaGame.Defeat))
			return EventBabaGame.Defeat;
		if(listEvent.contains(EventBabaGame.DestroyAll)) {
			for(Entity entiCell : this.plateau[x][y].content) {
				this.toDestroy.add(entiCell.getEntityId());
			}
			return EventBabaGame.DestroyAll;
		}
		if(listEvent.contains(EventBabaGame.Destroy)) {
			this.toDestroy.add(entityEntering.getEntityId());
			return EventBabaGame.Destroy;
		}
		if(listEvent.contains(EventBabaGame.Win))
			return EventBabaGame.Win;
		return EventBabaGame.Good;
	}

	public HashMap<Integer,ArrayList<PropertyEnum>> getPropertyHashMap() {
		return propertyHashMap;
	}
}