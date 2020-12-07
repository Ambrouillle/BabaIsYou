package babaIsYou;

import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.Property;
import babaIsYou.entity.Text;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.PropertyEnum;

import fr.umlv.zen5.*;
import java.awt.*;

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
        
        System.out.println(level.mooveProp(factory,PropertyEnum.You ,DirectionEnum.RIGHT));

        /*Application.run(Color.BLACK, context -> {
            GameArea game = new GameArea(context, level);
            game.run();
        });
        */
    }

}