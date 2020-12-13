package babaIsYou.entity;
import java.util.ArrayList;
import java.util.HashMap;

/*la factory creer a notre place l'objet, on lui delegue le new . Design Pattern Factory . Il semblerait
 * que ce soit plus propre et securisé*/
import babaIsYou.Level;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.NameEnum;
import babaIsYou.entity.entityEnum.OperatorEnum;
import babaIsYou.entity.entityEnum.PropertyEnum;

public class EntityFactory{	
	public HashMap<Integer,ArrayList<Element>> elementHashMap ; 	
	private Level level;
	
	public EntityFactory(Level level) {
		this.level = level;
		elementHashMap = new HashMap<Integer,ArrayList<Element>>() ;
	}
	public Name create(NameEnum name) {
		return new Name(name.getimageID(),name.getElemID());
	}
	
	public Operator create(OperatorEnum op) throws Exception {
		switch(op) {
		case Is:
			return new OperatorIs(op.getimageID(),op.getElemID());
		default:
			throw new Exception("Operator not Recognized");
		}
		
	}
	public Element create(ElementEnum el) {//i have to keep an ArrayLiost of all object.
		ArrayList<Element> list;
		Element ret = new Element(el.getimageID(),el.getElemID(),level);
		
		if(this.elementHashMap.containsKey(el.getElemID())) {
			list = this.elementHashMap.get(el.getElemID());
			list.add(ret);
		}
		else {
			list = new ArrayList<Element>();
			list.add(ret);
		}
		elementHashMap.put(el.getElemID(),list );
		return ret;
	}
		
		
	public Property create(PropertyEnum prop) {
		return new Property(prop.getimageID(),prop.getElemID());
	}
}

