package babaIsYou.entity;
/*la factory creer a notre place l'objet, on lui delegue le new . Design Pattern Factory . Il semblerait
 * que ce soit plus propre et securisé*/
import babaIsYou.Level;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.NameEnum;
import babaIsYou.entity.entityEnum.OperatorEnum;
import babaIsYou.entity.entityEnum.PropertyEnum;

public class EntityFactory{	
	private Level level;
	
	public EntityFactory(Level level) {
		this.level = level;
	}
	public Name create(NameEnum name) {
		return new Name(name.getimageID(),name.getElemID());
	}
	
	public Operator create(OperatorEnum op) throws Exception {
		switch(op) {
		case Is:
			return new OperatorIs(op.getimageID(),op.getElemID());
		default:
			throw new Exception("Operator non Regognize");
		}
		
	}
	public Element create(ElementEnum el) {
		return new Element(el.getimageID(),el.getElemID(),level);
	}
	public Property create(PropertyEnum prop) {
		return new Property(prop.getimageID(),prop.getElemID());
	}
}
