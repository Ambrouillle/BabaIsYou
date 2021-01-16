package babaIsYou;

import babaIsYou.entity.*;
import babaIsYou.entity.entityEnum.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {
	private Cell[][] plateau;
	private HashMap<Integer,ArrayList<PropertyEnum>> propertyHashMap ; 	
	private final ArrayList<Integer> toDestroy = new ArrayList<>();
	private final EntityFactory factory;

	/**
	 * Level Constructor.
	 * @param id Id of the level to load.
	 */
	public Level(String path,int id) {
		factory = new EntityFactory(this);
		this.LoadLevel(path ,id, getFactory());
	}

	/**
	 * Load the 'levelNumber' level.
	 * @param path Path of the file to load
	 * @param levelNumber Level ID used in the level file name.
	 * @param factory Game level factory.
	 */
	public void LoadLevel(String path, int levelNumber, EntityFactory factory){
		try {
			String row;
			String filepath = path;
			BufferedReader csvReader;
			try {
				csvReader = new BufferedReader(new FileReader(filepath));
			}catch (FileNotFoundException ex){
				filepath = path + File.separator + levelNumber + ".csv";
				csvReader = new BufferedReader(new FileReader(filepath));
			}

			row = csvReader.readLine();
			String[] data = row.split(",");
			int x = atoi(data[0]);
			int y = atoi(data[1]);
			plateau = new Cell[x][y];
			for(int i = 0; i < x; i++) {
				for(int j = 0; j < y; j++) { plateau[i][j] = new Cell(this,i,j); }
			}
			propertyHashMap = new HashMap<>() ;
			while((row = csvReader.readLine()) != null){
				synchronized (this){
					try {
						wait(15);
					} catch (InterruptedException ex) {
						System.out.println(ex);
					}
				}
				data = row.split(",");
				spawnEntity(factory, data);
			}
		}
		catch(IOException ex){
			System.out.println("No levels left");
			System.exit(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Allow the given name to subscribe to a cell.
	 * @param name Name that has to subscribe.
	 * @param x X-axis coordinate of the cell's position.
	 * @param y Y-axis coordinate of the cell's position.
	 */
	public void subscribeTo(Name name, int x, int y) {
		if(testOutOfBound(x, y)) {
			return;
		}
		plateau[x][y].subscribe(name);
	}

	/**
	 * Allow the given name to unsubscribe to a cell.
	 * @param name Name that has to unsubscribe.
	 * @param x X-axis coordinate of the cell's position.
	 * @param y Y-axis coordinate of the cell's position.
	 */
	public void unSubscribeTo(Name name, int x, int y) {
		if(testOutOfBound(x, y)) {
			return;
		}
		plateau[x][y].unSubscribe(name);
	}

	/**
	 * Add the Entity entity on the Cell x,y.
	 * @param entity Entity to add.
	 * @param x X-axis coordinate of the desired position.
	 * @param y Y-axis coordinate of the desired position.
	 */
	public void addEntityInCell(Entity entity,int x, int y) throws IOException {
		if(testOutOfBound(x, y))
			throw new IOException("Element placed outside of level.");
		entity.setx(x);
		entity.sety(y);
		getPlateau()[x][y].add(entity);//add
		getPlateau()[x][y].setChanged(true);
	}

	/**
	 * Check if the Cell x,y is in the Level.
	 * @param x X-axis coordinate of the position to check.
	 * @param y Y-axis coordinate of the position to check.
	 * @return True if (x,y) is outside the grid.
	 * */
	private boolean testOutOfBound(int x, int y) {
		return x >= getPlateau().length || y >= getPlateau()[0].length || x < 0 || y < 0;
	}


	/**
	 * Remove the Entity entity from this Cell.
	 * @param entity Entity that needs to be removed.
	 */
	public void removeEntityInCell(Entity entity) {
		getPlateau()[entity.getx()][entity.gety()].remove(entity);
		getPlateau()[entity.getx()][entity.gety()].setChanged(true);
	}



	/**
	 * Add the Property prop linked to the Element (idElem) in the Level.
	 * @param prop Property that have to be linked.
	 * @param idElement Element that we want to link the prop to.
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
			list = new ArrayList<>();
			list.add(prop);
			this.getPropertyHashMap().put(idElement,list);
		}
		
	}
	/**
	 * Call the same function in Cell that push entity in the next cell accordingly to direction.
	 * @param x X-axis coordinate of the destination's position.
	 * @param y Y-axis coordinate of the destination's position.
	 * @param entity Moved entity.
	 * @param direction Desired destination.
	 * @throws IOException
	 */
	public void pushedIn(int x, int y, Entity entity, DirectionEnum direction) throws IOException {

		if(testOutOfBound(x, y))
			return;
		this.getPlateau()[x][y].pushedIn(entity, direction);

	}
	/**
	 * Remove the Property prop from Element elem in this.Level.
	 * @param prop Property to remove.
	 * @param idElement id of the concerned element.
	 */
	public void removePropInMap(PropertyEnum prop,int idElement ) {
		if(this.getPropertyHashMap().containsKey(idElement)) {
			if(this.getPropertyHashMap().get(idElement).contains(prop)) {
				this.getPropertyHashMap().get(idElement).remove(prop);
			}
		}
	}

	/**
	 * Call the same function  in Cell that check if is possible to enter in the next Cell.
	 * @param x X-axis coordinate of the position to check.
	 * @param y Y-axis coordinate of the position to check.
	 * @param direction Direction wanted.
	 * @return True if allowed to move here.
	 *			False if not.
	 */
	public boolean canEnter( int x,int y, Entity entity, DirectionEnum direction) {
		if(testOutOfBound(x, y)) {
			return false;
		}
		return this.getPlateau()[x][y].canEnter(entity, direction);
	}
	/**
	 * Function that move the Entity entity in the Direction.
	 * @param entity Entity to move.
	 * @param direction Direction to move it to.
	 * @throws IOException 
	 */
	public EventBabaGame move(Entity entity, DirectionEnum direction) throws IOException {
		int x = entity.getx()+direction.getmoveX();
		int y = entity.gety()+direction.getmoveY();
		if(testOutOfBound(x, y))
			return EventBabaGame.Stop;
		if( this.getPlateau()[x][y].enter(entity, direction))
			return atEnterInCell(entity, x, y);
		return EventBabaGame.Stop;
	}


	/**
	 * Remove entities in cell x y.
	 * @param x X-axis coordinate of the desired cell.
	 * @param y Y-axis coordinate of the desired cell.
	 */
	public void clearCell(int x, int y){
		if(!testOutOfBound(x,y)) {
			for (Entity e : this.plateau[x][y].getContent()) {
				toDestroy.add(e.getEntityId());
			}
		}
	}

	/**
	 * Function that gives the list of the elements with the specified property.
	 * @param prop Property searched.
	 * @return idElement's ArrayList of the objects with 'prop' property.
	 */
	public List<Integer> getElemWithProp(PropertyEnum prop) {
		ArrayList<Integer> list = new ArrayList<>();
		for (Map.Entry<Integer, ArrayList<PropertyEnum>> mapentry : getPropertyHashMap().entrySet()) {
	           if((mapentry.getValue()).contains(prop)) {
	        	   list.add(mapentry.getKey());
	           }
	        }
		return list;
		}

	
	/**
	 * Move all elements with the Property 'prop' to the next Cell(according to direction).
	 * @param factory Level Factory.
	 * @param prop Property that needs to be moved.
	 * @param direction Direction headed to.
	 * @return  True if there is at least one element with the prop.
	 * 			False if there is no element with the prop.
	 * @throws IOException 
	 */
	public EventBabaGame moveProp(EntityFactory factory, PropertyEnum prop, DirectionEnum direction) throws IOException {
		ArrayList<Element> list =new ArrayList<>();
		for (int idElem  : getElemWithProp(prop)) {
			list.addAll(factory.getElementHashMap().get(idElem));
		}
		if(prop != null) {
			for(Element el : list) {
				if(move(el,direction) == EventBabaGame.Win)
						return EventBabaGame.Win;
			}
			return EventBabaGame.Good;
		}
		
		return EventBabaGame.Defeat;
		
	}

	/**
	 *Trigger for detonate property.
	 */
	public void detonate(){
		ArrayList<Element> list =new ArrayList<>();
		for (int idElem  : getElemWithProp(PropertyEnum.Explode)) {
			list.addAll(factory.getElementHashMap().get(idElem));
		}
		if(getElemWithProp(PropertyEnum.Explode) != null) {
			for(Element el : list) {
				el.explode();
			}
		}
	}


	/**
	 * Destroy the Entity 'ent' from the Level and the factory.
	 * @param factory Game level factory.
	 * @param ent Entity to remove.
	 */
	public void removeEntityFromEveryWhere(EntityFactory factory, Entity ent) {
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

	/**
	 * Removes all entity inside 'toDestroy' using removeEntityFromEveryWhere
	 */
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
			removeEntityFromEveryWhere(factory, target);
		}
		toDestroy.clear();
	}

	/**
	 * Spawn the entity specified in the formatted data string.
	 * @param factory Game level Factory.
	 * @param data Formatted data extracted from level file.
	 */
	public void spawnEntity(EntityFactory factory,String[] data) throws Exception {
		switch (atoi(data[0])){
			case 1://Element
				if (atoi(data[4]) == 1){
					String[] x_val = data[2].split("-");
					String[] y_val = data[3].split("-");
					for(int i = atoi(x_val[0]);i <= atoi(x_val[1]); i++){
						for (int j = atoi(y_val[0]);j <= atoi(y_val[1]); j++) {
							this.addEntityInCell(factory.create(ElementEnum.valueOf(data[1])), i, j);
							this.getPlateau()[i][j].setChanged(true);
						}
					}
				} else {
					this.addEntityInCell(factory.create(ElementEnum.valueOf(data[1])), atoi(data[2]), atoi(data[3]));
					this.getPlateau()[atoi(data[2])][atoi(data[3])].setChanged(true);
				}
				break;
			case 2://Property
				this.addEntityInCell(factory.create(PropertyEnum.valueOf(data[1])),atoi(data[2]),atoi(data[3]));
				this.getPlateau()[atoi(data[2])][atoi(data[3])].setChanged(true);
				break;
			case 3://Name
				Name name = factory.create(NameEnum.valueOf(data[1]));
				this.addEntityInCell(name,atoi(data[2]),atoi(data[3]));
				this.getPlateau()[atoi(data[2])][atoi(data[3])].setChanged(true);
				break;
				
			case 4://Operator
				Operator op = factory.create(OperatorEnum.valueOf(data[1]));
				this.addEntityInCell(op,atoi(data[2]),atoi(data[3]));
				this.getPlateau()[atoi(data[2])][atoi(data[3])].setChanged(true);
				break;
		}
	}

	/**
	 * Turn the 'nb' string to an int version.
	 * @param nb String containing a number.
	 * @return Int corresponding to nb.
	 */
	int atoi(String nb){
		int result = 0;
		for (int i = 0; i < nb.length(); i++)
			result = result * 10 + nb.charAt(i) - '0';
		return result;
	}

	/**
	 * Notify if the game is lost.
	 * @param factory Game level factory.
	 * @return Defeat if the game is lost, Good any other way.
	 */
	public EventBabaGame isLost(EntityFactory factory){
		for (int idElem  : getElemWithProp(PropertyEnum.You)) {
			if(factory.getElementHashMap().get(idElem) != null && factory.getElementHashMap().get(idElem).size() > 0)
				return EventBabaGame.Good;
		}

		
		return EventBabaGame.Defeat;
	}

	/**
	 * Check what happen when entity will go to the Cell x,y.
	 * @param entityEntering Entity that enters the cell.
	 * @param x X-axis coordinate of the cell's position.
	 * @param y Y-axis coordinate of the cell's position.
	 * @return Stop if the entity can't move, Win if game is won, Defeat if it is lost, else returns Good.
	 */
	public EventBabaGame atEnterInCell(Entity entityEntering, int x, int y) {
		if(testOutOfBound(x,y)){
			return EventBabaGame.Stop;
		}
		List<EventBabaGame> listEvent = new ArrayList<>();
		
		for(Entity entiCell : this.getPlateau()[x][y].getContent()) {
			if(entiCell.getEntityId() != entityEntering.getEntityId()) {
				listEvent.add(entiCell.isEnteredBy(entityEntering));
			}
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
		if(listEvent.contains(EventBabaGame.Explode)){
			detonate();
		}
		if(listEvent.contains(EventBabaGame.Destroy)) {
			this.toDestroy.add(entityEntering.getEntityId());
			return EventBabaGame.Destroy;
		}
		if(listEvent.contains(EventBabaGame.Win))
			return EventBabaGame.Win;
		return EventBabaGame.Good;
	}

	/**
	 * PropertyHasMap getter.
	 * @return PropertyHashMap.
	 */
	public Map<Integer,ArrayList<PropertyEnum>> getPropertyHashMap() {
		return propertyHashMap;
	}


	/**
	 * Factory getter.
	 * @return Factory.
	 */
	public EntityFactory getFactory() {
		return factory;
	}


	/**
	 * Plateau getter.
	 * @return Plateau.
	 */
	public Cell[][] getPlateau() {
		return plateau;
	}

}