package babaIsYou;

import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.Property;
import babaIsYou.entity.Text;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.PropertyEnum;

public class Main {
    public static void main(String[] args) {
        Level level = new Level(6,6); // creation level
        EntityFactory factory = new EntityFactory(level); // creation de l'usine a creation
        
        Element rock = factory.create(ElementEnum.Rock); /// creatioon Element rock
        Element rock2 = factory.create(ElementEnum.Rock);
        Element rock3 = factory.create(ElementEnum.Rock);
        Element rock4 = factory.create(ElementEnum.Rock);
        Element rock5 = factory.create(ElementEnum.Rock);
        
        Element wall = factory.create(ElementEnum.Wall);
        Element wall2 = factory.create(ElementEnum.Wall);
        
        level.addPropInMap(PropertyEnum.Push, rock.getElemID()); //ajout de la property rosck is push sur la map
        level.addPropInMap(PropertyEnum.You, ElementEnum.Wall.getElemID()); //ajout de la property wall is you sur la map
        System.out.println(level.propertyHashMap);
        
        level.addEntityInCell(wall,0,3);
        level.addEntityInCell(rock,1,3); 	//rock ajouter a la cell[1][3]
        level.addEntityInCell(rock2,2,3); 
        level.addEntityInCell(rock3,3,3); 
        level.addEntityInCell(rock4,4,3); 
        level.addEntityInCell(rock5,1,4); 
        level.addPropInMap(PropertyEnum.Push, rock.getElemID() ); // ajout une deuxieme fois de la meme prop sur rock --> no pb
        System.out.println("rock is push ? "+ rock.isPush()); // rock est bien push

        System.out.println("wall, x"+wall.getx()+" , y :"+wall.gety());
        System.out.println("rock, x: "+ rock.getx()+", y: "+rock.gety() );
        System.out.println("rock2, x: "+ rock2.getx()+", y: "+rock2.gety() );
        System.out.println("rock3, x: "+ rock3.getx()+", y: "+rock3.gety() );
        System.out.println("rock4, x: "+ rock4.getx()+", y: "+rock4.gety() );
        System.out.println("rock5, x: "+ rock5.getx()+", y: "+rock5.gety() );
        
        System.out.println("moove in action");
        //level.moove(rock,DirectionEnum.DOWN);
      
        
        System.out.println(level.mooveProp(factory,PropertyEnum.You ,DirectionEnum.RIGHT));

        
        System.out.println("wall, x"+wall.getx()+" , y :"+wall.gety());
        System.out.println("rock, x: "+ rock.getx()+", y: "+rock.gety() );
        System.out.println("rock2, x: "+ rock2.getx()+", y: "+rock2.gety() );
        System.out.println("rock3, x: "+ rock3.getx()+", y: "+rock3.gety() );
        System.out.println("rock4, x: "+ rock4.getx()+", y: "+rock4.gety() );
        System.out.println("rock5, x: "+ rock5.getx()+", y: "+rock5.gety() );
        System.out.println(factory.elementHashMap);
        level.removeEntityfromEveryWhere(factory, wall);
        System.out.println(factory.elementHashMap);
        System.out.println(level.propertyHashMap.get(wall.getElemID()));
    }

}