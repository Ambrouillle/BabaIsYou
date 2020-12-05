package babaIsYou;

import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.Property;
import babaIsYou.entity.Text;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.PropertyEnum;
import babaIsYou.Visual;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;

public class Main {
    public static void main(String[] args) {
        Level level = new Level(6,6); // creation level
        EntityFactory factory = new EntityFactory(level); // creation de l'usine a creation
        
        Element rock = factory.create(ElementEnum.Rock); /// creatioon Element rock
        Element rock2 = factory.create(ElementEnum.Rock);
        Element rock3 = factory.create(ElementEnum.Rock);
        Element rock4 = factory.create(ElementEnum.Rock);
        Element rock5 = factory.create(ElementEnum.Rock);
        
        level.addPropInMap(PropertyEnum.Push, rock.getElemID()); //ajout de la property rosck is push sur la map
        System.out.println(level.propertyHashMap);
        level.addEntityInCell(factory.create(ElementEnum.Baba), 1, 1);
        level.addEntityInCell(factory.create(ElementEnum.Flag), 2, 1);
        level.addEntityInCell(factory.create(ElementEnum.Wall), 3, 1);
        level.addEntityInCell(factory.create(ElementEnum.Water), 1, 2);
        level.addEntityInCell(factory.create(ElementEnum.Skull), 2, 2);
        level.addEntityInCell(factory.create(ElementEnum.Lava), 3, 2);
        level.addEntityInCell(factory.create(ElementEnum.Rock), 2, 3);

        Application.run(Color.BLACK, context -> {
            GameArea game = new GameArea(context, level);
            game.run();
        });
    }

}