package babaIsYou;

import java.io.*;
import java.util.*;


import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.Name;
import babaIsYou.entity.Operator;
import babaIsYou.entity.Property;
import babaIsYou.entity.entityEnum.*;

public class Level {
	private Cell[][] plateau;
	private HashMap<Integer,ArrayList<PropertyEnum>> propertyHashMap ; 	
	private ArrayList<Integer> toDestroy = new ArrayList<>();
	private EntityFactory factory;
	private int x;
	private int y;

	public Level(int id) {
		factory = new EntityFactory(this);
		this.LoadLevel(id, getFactory());
	}
	
	/**
	 * Function subscribing the 2 Cells on the Name's right and the 2 Cells under the Name
	 * @param name
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean subscribeTo(Name name, int x,int y) {
		if(testOutOfBound(x, y)) {
			return false;
		}
		plateau[x][y].subscribe(name);
		return true;
	}
	
	
	/**
	 * Function Unsubscribing the 2 Cells on the Name's right and the 2 Cells under the Name
	 * @param name
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean unSubscribeTo(Name name, int x,int y) {
		if(testOutOfBound(x, y)) {
			return false;
		}
		plateau[x][y].unSubscribe(name);
		return true;
	}
	
	/**
	 * add the Entity entity on the Cell x,y
	 * @param entity
	 * @param x
	 * @param y
	 */
	public void addEntityInCell(Entity entity,int x, int y) {
		if(testOutOfBound(x, y))
			throw new RuntimeException("element placed outside of level");
		entity.setx(x);
		entity.sety(y);
		getPlateau()[x][y].add(entity);//add
	}

		/**
		 * check if the Cell x,y is in the Level
		 * @param x
		 * @param y
		 * @return
		 */
	private boolean testOutOfBound(int x, int y) {
		return x >= getPlateau().length || y >= getPlateau()[0].length || x < 0 || y < 0;
	}
	
	/**
	 * remove the Entity entity from his Cell
	 * @param entity
	 */
	public void removeEntityInCell(Entity entity) {
		getPlateau()[entity.getx()][entity.gety()].remove(entity);
	}
	
	
	
	/**
	 * add the Property prop linked to the Element (idElem) in the Level
	 * @param prop
	 * @param idElement
	 */
	public void addPropInMap(PropertyEnum prop, int idElement ) {
		ArrayList<PropertyEnum> list;
		if(this.getPropertyHashMap().containsKey(idElement)) {
			list = this.getPropertyHashMap().get(idElement);
			if(!list.contains((prop))){
				list.add(prop);
				}
		}
		else {
			list = new ArrayList<PropertyEnum>();
			list.add(prop);
			this.getPropertyHashMap().put(idElement,list);
		}
		
	}
	/**
	 * call the same function in Cell that push entity in the next cell accordingly to direction
	 * @param x
	 * @param y
	 * @param entity
	 * @param direction
	 * @return
	 */
	public boolean pushedIn(int x,int y,Entity entity, DirectionEnum direction) {

		if(testOutOfBound(x, y))
			return false;
		return this.getPlateau()[x][y].pushedIn(entity, direction);	
	
	}
	/**
	 * remove the Property prop accordingly to Element elem in this.Level
	 * @param prop
	 * @param elem
	 */
	public void removePropInMap(PropertyEnum prop,int idElement ) {
		if(this.getPropertyHashMap().containsKey(idElement)) {
			if(this.getPropertyHashMap().get(idElement).contains(prop)) {
				this.getPropertyHashMap().get(idElement).remove(prop);
			}
		}
	}

	/**
	 * Check if there is any object STOP is the Cell[x][y]or if the Cell is out of the map
	 * @return true if there is an object STOP
	 * 		   false if not
	 */
	public boolean isCaseisStop(int x, int y){
		if(testOutOfBound(x, y))
			return true;
		return this.getPlateau()[x][y].isStop();
	}
	
	/**
	 * Call the same function  in Cell that check if is possible to enter in the next Cell
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
		return this.getPlateau()[x][y].canEnter(entity, direction);
	}
	/**
	 * function that moove the Entity entity in the Direction
	 * @param entity
	 * @param direction
	 */
	public EventBabaGame moove(Entity entity, DirectionEnum direction) {
		int x = entity.getx()+direction.getmoveX();
		int y = entity.gety()+direction.getmoveY();
		if(testOutOfBound(x, y))
			return EventBabaGame.Stop;
		if( this.getPlateau()[x][y].enter(entity, direction))
			return atEnterInCell(entity, x, y, entity.getEntityId());
		return EventBabaGame.Stop;
	}
	/**
	 * function that return the ArrayList of the idElem of the object according to the prop
	 * @param prop
	 * @return ArrayList of the idElem of the object according to the prop
	 */
	public List<Integer> getElemnwithProp(PropertyEnum prop) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (Map.Entry<Integer, ArrayList<PropertyEnum>> mapentry : getPropertyHashMap().entrySet()) {
	           if((mapentry.getValue()).contains(prop)) {
	        	   list.add(mapentry.getKey());
	           }
	        }
		return list;
		}

	
	/**
	 * moove all elements with the Property prop to the next Cell( according to direction)
	 * @param factory
	 * @param prop
	 * @param direction
	 * @return  true if there is at least one element according to the prop
	 * 			false if there is no element with the prop
	 */
	public EventBabaGame mooveProp(EntityFactory factory,PropertyEnum prop,DirectionEnum direction) {
		ArrayList<Element> list =new ArrayList<>();
		for (int idElem  :getElemnwithProp(prop)) {
			list.addAll(factory.getElementHashMap().get(idElem));
		}
		if(prop != null) {
			for(Element el : list) {
				if(moove(el,direction) == EventBabaGame.Win)
						return EventBabaGame.Win;
			}
			return EventBabaGame.Good;
		}
		
		return EventBabaGame.Defeat;
		
	}
	
	
	/**
	 * destroy the Entity ent from the Level and the factory
	 * @param factory
	 * @param ent
	 */
	public void removeEntityfromEveryWhere(EntityFactory factory, Entity ent) {
		if (ent == null) return;
		if(ent instanceof  Element) {
			//remove ent from the factory
			if(factory.getElementHashMap().containsKey(((Element) ent).getElemID())) {
				ArrayList<Element> list = factory.getElementHashMap().get(((Element) ent).getElemID());
				if(list.size() == 1) {
					//check if there is a prop associed and remove it from level.propertyHashMap
					if(this.getPropertyHashMap().containsKey(((Element) ent).getElemID())) {
						this.getPropertyHashMap().remove(((Element) ent).getElemID()) ;
					}
				}
				list.remove(ent);
				factory.getElementHashMap().put(((Element) ent).getElemID(), list);
			}
			//remove ent from cell
			removeEntityInCell(ent);
			
		}
	}

	public void removeFromToDestroy(){
		Entity target = null;
		for(Integer id : toDestroy){
			for(ArrayList<Element> list :factory.getElementHashMap().values()){
				for(Element e : list){
					if (e.getEntityId() == id){
						target = e;
					}
				}
			}
			removeEntityfromEveryWhere(factory, target);
		}
		toDestroy.clear();
	}
	
	
	
	public void spawnEntity(EntityFactory factory,String[] data) throws Exception {
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
				Name name = factory.create(NameEnum.valueOf(data[1]));
				this.addEntityInCell(name,atoi(data[2]),atoi(data[3]));
				break;
				
			case 4://Operator
				Operator op = factory.create(OperatorEnum.valueOf(data[1]));
				this.addEntityInCell(op,atoi(data[2]),atoi(data[3]));
				break;
		}
	}

	
	public void LoadLevel(int levelNumber, EntityFactory factory){
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
			System.err.println("An exception occurred");
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int atoi(String nb){
		int result = 0;
		for (int i = 0; i < nb.length(); i++)
			result = result * 10 + nb.charAt(i) - '0';
		return result;
	}

	public EventBabaGame isLost(EntityFactory factory){
		for (int idElem  :getElemnwithProp(PropertyEnum.You)) {
			if(factory.getElementHashMap().get(idElem) != null && factory.getElementHashMap().get(idElem).size() > 0)
				return EventBabaGame.Good;
		}

		
		return EventBabaGame.Defeat;
	}

	public EventBabaGame atEnterInCell(Entity entityEntering,int x, int y,int id) {
		//check what happen when entity will go to the Cell x,y
		//if win
		if(testOutOfBound(x,y)){
			return EventBabaGame.Stop;
		}
		List<EventBabaGame> listEvent = new ArrayList<>();
		
		for(Entity entiCell : this.getPlateau()[x][y].getContent()) {
			listEvent.add(entiCell.isEnteredBy(entityEntering));
		}
		if(listEvent.contains(EventBabaGame.Defeat)) {
			this.toDestroy.add(entityEntering.getEntityId());
			return EventBabaGame.Destroy;
		}
		if(listEvent.contains(EventBabaGame.DestroyAll)) {
			for(Entity entiCell : this.getPlateau()[x][y].getContent()) {
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

	public Map<Integer,ArrayList<PropertyEnum>> getPropertyHashMap() {
		return propertyHashMap;
	}



	public EntityFactory getFactory() {
		return factory;
	}



	public Cell[][] getPlateau() {
		return plateau;
	}
	public void OperatorInteract(Operator op){
        OperatorAux(op,1,0);
        OperatorAux(op,0,1);
    }
    
    private void OperatorAux(Operator op,int x,int y){
        if (!testOutOfBound(op.getx()-x, op.gety()-y)){
            for(Entity e : this.plateau[op.getx()-x][op.gety()-y].getContent()){
                if (e.getClass() == Name.class){
                    if(!testOutOfBound(op.getx()+x, op.gety()+y)){
                        for(Entity f : this.plateau[op.getx()+x][op.gety()+y].getContent()){
                            if (f.getClass() == Property.class){
                                this.addPropInMap(PropertyEnum.valueOf(f.getEntityName()),
                                        ElementEnum.valueOf(e.getEntityName()).getElemID());
                                System.out.println(f.getEntityName() + e.getEntityName());
                            }
                        }
                    }
                }
            }
        }
    }
}