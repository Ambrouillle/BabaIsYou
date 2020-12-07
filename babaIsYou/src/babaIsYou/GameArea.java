package babaIsYou;

import babaIsYou.*;
import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.PropertyEnum;
import fr.umlv.zen5.*;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;

import java.awt.geom.Rectangle2D;
import java.awt.Color;

public class GameArea {
    ApplicationContext context;
    float Width;
    float Height;
    Level lvl;

    GameArea(ApplicationContext context){
        this.context = context;
        ScreenInfo info = context.getScreenInfo();
        this.Width = info.getWidth();
        this.Height = info.getHeight();
    }
    void checkMovement(Action action, Event event, EntityFactory factory){
        if (event.getKey() == KeyboardKey.UP) {
            lvl.mooveProp(factory, PropertyEnum.You , DirectionEnum.UP);
        }
        else if (event.getKey() == KeyboardKey.LEFT) {
            lvl.mooveProp(factory,PropertyEnum.You ,DirectionEnum.LEFT);
        }
        else if (event.getKey() == KeyboardKey.RIGHT) {
            lvl.mooveProp(factory,PropertyEnum.You ,DirectionEnum.RIGHT);
        }
        else if (event.getKey() == KeyboardKey.DOWN) {
            lvl.mooveProp(factory,PropertyEnum.You ,DirectionEnum.DOWN);
        }
    }

    public void run(){
        lvl = new Level(6,6); // creation lvl
        EntityFactory factory = new EntityFactory(lvl); // creation de l'usine a creation
        lvl.LoadLevel(1,factory);
        Visual.Area area = new Visual.Area();
        int lvly = lvl.plateau.length ;
        int lvlx = lvl.plateau[0].length;
        int boardOriginx = (int)(this.Width / 2) - (100 * lvly/2);
        int boardOriginy = (int)(this.Height / 2) - (100 * lvlx/2);
        area.RefreshScreen(context,(int)this.Width,(int)this.Height,lvl,boardOriginx,boardOriginy);
        for(;;) {
            Event event = context.pollOrWaitEvent(10);
            if (event == null) {  // no event
                continue;
            }
            Action action = event.getAction();
            if (event.getKey() == KeyboardKey.L) {
                context.exit(0);
            }
            else if (action == Action.KEY_PRESSED){
                checkMovement(action,event,factory);
                area.RefreshScreen(context,(int)this.Width,(int)this.Height,lvl,boardOriginx,boardOriginy);
            }
        }
    }
}
