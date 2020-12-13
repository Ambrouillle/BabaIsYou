package babaIsYou;

import babaIsYou.*;
import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.Events;
import babaIsYou.entity.entityEnum.PropertyEnum;
import babaIsYou.Level;
import fr.umlv.zen5.*;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;

import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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
    Events checkMovement(Action action, Event event, EntityFactory factory){
        Events ev;
        ev = Events.Good;
        int i;

        i = 0;
        if (List.of("UP","DOWN","LEFT","RIGHT").contains(event.getKey().name())) {
            DirectionEnum dir = DirectionEnum.valueOf(event.getKey().name());
            ArrayList<Element> list = factory.elementHashMap.get(lvl.getElemnwithProp(PropertyEnum.You));
            for(Entity en : list) {
                ev = lvl.atEnterInCell(en, en.getx() +dir.getmoveX(), en.gety()+ dir.getmoveY(), factory, i);
                i += 1;
            }
            lvl.mooveProp(factory, PropertyEnum.You, dir);
            if (lvl.toDestroy != null) {
                for (Integer id : lvl.toDestroy) {
                    lvl.removeEntityfromEveryWhere(factory,list.get(id));
                }
            }
            return ev;
        }
        return Events.Good;
    }

    public void run(){
        lvl = new Level(6,6); // creation lvl
        EntityFactory factory = new EntityFactory(lvl); // creation de l'usine a creation
        lvl.LoadLevel(1,factory);
        lvl.addPropInMap(PropertyEnum.You, ElementEnum.Baba.getElemID());
        lvl.addPropInMap(PropertyEnum.Defeat, ElementEnum.Baba.getElemID());
        Visual.Area area = new Visual.Area();
        Events ev;
        ev = Events.Good;
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
            if (ev != Events.Defeat && ev != Events.Win) {
                if (action == Action.KEY_PRESSED) {
                    ev = checkMovement(action, event, factory);
                    if (lvl.isLost(factory) == Events.Defeat) {
                        ev = Events.Defeat;
                    }
                    area.RefreshScreen(context, (int) this.Width, (int) this.Height, lvl, boardOriginx, boardOriginy);
                }
            }
            if (ev == Events.Defeat){
                area.Loose(context,(int)this.Width, (int)this.Height);
            }
            else if(ev == Events.Win){
                area.Win(context,(int)this.Width, (int)this.Height);
            }
        }
    }
}
