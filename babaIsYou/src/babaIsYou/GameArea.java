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
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class GameArea {
    ApplicationContext context;
    float Width;
    float Height;
    Level lvl;
    int lvlx;
    int lvly;
    int boardOriginx;
    int boardOriginy;
    Visual.Area area;

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
        DirectionEnum dir;
        ArrayList<Element> list;

        i = 0;

        if (List.of("UP","DOWN","LEFT","RIGHT").contains(event.getKey().name())) {
            dir = DirectionEnum.valueOf(event.getKey().name());
            list = factory.elementHashMap.get(lvl.getElemnwithProp(PropertyEnum.You));

            for(Entity en : list) {
                ev = lvl.atEnterInCell(en, en.getx() +dir.getmoveX(), en.gety()+ dir.getmoveY(), i);
                if (ev == Events.Defeat){lvl.mooveProp(factory, PropertyEnum.You, dir);return ev;}
                i += 1;
            }

            lvl.mooveProp(factory, PropertyEnum.You, dir);
            if (lvl.toDestroy != null) {
                for (Integer id : lvl.toDestroy) {

                    lvl.removeFromToDestroy(factory);
                }
            }
            return lvl.isLost(factory) == Events.Good ? ev : Events.Defeat;
        }
        return Events.Good;
    }

    public void setSize(){
        this.lvly = lvl.plateau.length ;
        this.lvlx = lvl.plateau[0].length;
        area = new Visual.Area(this.Height/lvlx, this.Width/lvly);
        this.boardOriginx = (int)(this.Width / 2) - (area.imagesSize * lvly/2);
        this.boardOriginy = (int)(this.Height / 2) - (area.imagesSize * lvlx/2);
    }

    public int run(int id){
        int return_val;
        lvl = new Level(id); // creation lvl
        lvl.addPropInMap(PropertyEnum.You, ElementEnum.Baba.getElemID());
        lvl.addPropInMap(PropertyEnum.Push, ElementEnum.Rock.getElemID());
        lvl.addPropInMap(PropertyEnum.Sink,ElementEnum.Water.getElemID());
        Events ev;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setSize();
        return_val = 1;
        ev = Events.Good;
        area.RefreshScreen(context,(int)this.Width,(int)this.Height,lvl,boardOriginx,boardOriginy);
        for(;;) {
            Event event = context.pollOrWaitEvent(10);
            if (event == null) {  // no event
                continue;
            }
            Action action = event.getAction();
            if (event.getKey() == KeyboardKey.L) {
                return return_val;
            }
            if (ev != Events.Defeat && ev != Events.Win) {
                if (action == Action.KEY_PRESSED) {
                    ev = checkMovement(action, event, lvl.factory);
                    if (lvl.isLost(lvl.factory) == Events.Defeat) {
                        ev = Events.Defeat;
                    }
                    area.RefreshScreen(context, (int) this.Width, (int) this.Height, lvl, boardOriginx, boardOriginy);
                }
            }
            if (ev == Events.Defeat){
                area.Loose(context,(int)this.Width, (int)this.Height);
            }
            else if(ev == Events.Win){
                return_val = 2;
                area.Win(context,(int)this.Width, (int)this.Height);
            }
            toolkit.sync();
        }
    }
}
