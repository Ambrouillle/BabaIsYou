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
	private int id;
	public EntityFactory(Level level) {
		id = 0;
		this.level = level;
		elementHashMap = new HashMap<Integer,ArrayList<Element>>() ;
	}
	public Name create(NameEnum name) {
		Name ret = new Name(name.getimageID(),id,name.getElemID());
		id += 1;
		return ret;
	}
	
	public Operator create(OperatorEnum op) throws Exception {
		switch(op) {
		case Is:
			Operator ret = new OperatorIs(op.getimageID(),id,op.getElemID());
			id += 1;
			return ret;
		default:
			throw new Exception("Operator not Recognized");
		}
		
	}
	public Element create(ElementEnum el) {//i have to keep an ArrayLiost of all object.
		ArrayList<Element> list;
		Element ret = new Element(el.getimageID(),this.id,el.getElemID(),level);
		id += 1;
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
		Property ret = new Property(prop.getimageID(),this.id,prop.getElemID());
		id += 1;
		return ret;
	}
}

