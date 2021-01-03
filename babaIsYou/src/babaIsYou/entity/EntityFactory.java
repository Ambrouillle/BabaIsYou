package babaIsYou.entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import babaIsYou.Level;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.NameEnum;
import babaIsYou.entity.entityEnum.OperatorEnum;
import babaIsYou.entity.entityEnum.PropertyEnum;


public class EntityFactory{	
	private Map<Integer,ArrayList<Element>> elementHashMap ; 	
	private final Level level;
	private int idEntity;
	
	
	public EntityFactory(Level level) {
		idEntity = 0;
		this.level = level;
		setElementHashMap(new HashMap<>()) ;
	}
	public Name create(NameEnum name) {
		Name ret = new Name(name.getimageID(),idEntity,name.getElemID(),level);
		idEntity += 1;
		return ret;
	}
	
	public Operator create(OperatorEnum op) throws Exception {
		switch(op) {
		case Is:
			Operator ret = new OperatorIs(op.getimageID(),idEntity,op.getElemID(),level);
			idEntity += 1;
			return ret;
		default:
			throw new RuntimeException("Operator not Recognized");
		}
		
	}
	public Element create(ElementEnum el) {//i have to keep an ArrayLiost of all object.
		ArrayList<Element> list;
		Element ret = new Element(el.getimageID(),this.idEntity,el.getElemID(),level);
		idEntity += 1;
		if(this.getElementHashMap().containsKey(el.getElemID())) {
			list = this.getElementHashMap().get(el.getElemID());
			list.add(ret);
		}
		else {
			list = new ArrayList<>();
			list.add(ret);
		}
		getElementHashMap().put(el.getElemID(),list );
		return ret;
	}
		
		
	public Property create(PropertyEnum prop) {
		Property ret = new Property(prop.getimageID(),this.idEntity,prop.getElemID(),level);
		idEntity += 1;
		return ret;
	}
	public Map<Integer,ArrayList<Element>> getElementHashMap() {
		return elementHashMap;
	}
	public void setElementHashMap(Map<Integer,ArrayList<Element>> elementHashMap) {
		this.elementHashMap = elementHashMap;
	}
}

